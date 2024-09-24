package com.marossolutions.skeletonmultiplatform.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.marossolutions.skeletonmultiplatform.model.Airport
import com.marossolutions.skeletonmultiplatform.navigation.SimpleNavigator
import com.marossolutions.skeletonmultiplatform.navigation.screens.ScreenDetail
import com.marossolutions.skeletonmultiplatform.repository.AirportRepository
import com.marossolutions.skeletonmultiplatform.service.ApplicationCloseService

internal class HomeViewModel(
    private val airportRepository: AirportRepository,
    private val navigator: SimpleNavigator,
    private val applicationCloseService: ApplicationCloseService,
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)

    internal val uiState = combine(
        flow = airportRepository.airports,
        flow2 = _isLoading,
        flow3 = airportRepository.isErrorOccurred,
        transform = ::Triple,
    ).map { (airports, isLoading, errorOccurred) ->
        when {
            airports.isEmpty() || isLoading -> HomeUiState.Loading
            errorOccurred -> HomeUiState.Error
            airports.isNotEmpty() -> HomeUiState.Content(airports)
            else -> error("Unsupported UI state scenario")
        }
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(),
        initialValue = HomeUiState.Loading
    )

    init {
        viewModelScope.launch {
            _isLoading.value = true
            airportRepository.fetchAirports()
            _isLoading.value = false
        }
    }

    internal fun clearSelectedAirport() {
        airportRepository.clearSelectedAirport()
    }

    internal fun navigateToAirportDetail(icao: String) {
        navigator.navigateTo(ScreenDetail(icao))
    }

    internal fun exitFromApplication() {
        viewModelScope.launch {
            applicationCloseService.closeApplication()
        }
    }

    internal sealed interface HomeUiState {
        data object Loading : HomeUiState

        data class Content(
            val airports: List<Airport>,
        ) : HomeUiState

        data object Error : HomeUiState
    }
}
