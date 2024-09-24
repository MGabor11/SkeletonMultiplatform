package com.marossolutions.skeletonmultiplatform.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.marossolutions.skeletonmultiplatform.model.Airport
import com.marossolutions.skeletonmultiplatform.model.Schedule
import com.marossolutions.skeletonmultiplatform.navigation.SimpleNavigator
import com.marossolutions.skeletonmultiplatform.navigation.screens.ScreenDetail
import com.marossolutions.skeletonmultiplatform.repository.AirportRepository
import com.marossolutions.skeletonmultiplatform.repository.ScheduleRepository

class DetailViewModel(
    private val airportRepository: AirportRepository,
    private val scheduleRepository: ScheduleRepository,
    private val navigator: SimpleNavigator,
    private val mapsKey: String,
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)

    internal val uiState = combine(
        airportRepository.airportDetail,
        scheduleRepository.airportSchedules,
        _isLoading,
        ::Triple,
    ).map { (airport, schedules, isLoading) ->
        when {
            isLoading -> DetailUiState.Loading
            airport != null -> DetailUiState.Content(
                airport = airport,
                mapsKey = mapsKey,
                schedules = schedules ?: emptyList(),
            )

            else -> error("Unsupported UI state scenario")
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = DetailUiState.Loading
    )

    init {
        viewModelScope.launch {
            val selectedICAO = navigator.currentAppScreen.value.let { (it as? ScreenDetail)?.icao }
            airportRepository.fetchAirportDetails(checkNotNull(selectedICAO))
            scheduleRepository.fetchAirportSchedule(selectedICAO)
            _isLoading.value = false
        }
    }

    internal sealed interface DetailUiState {
        data object Loading : DetailUiState

        data class Content(
            val airport: Airport,
            val mapsKey: String,
            val schedules: List<Schedule>,
        ) : DetailUiState

        data object Error : DetailUiState
    }
}