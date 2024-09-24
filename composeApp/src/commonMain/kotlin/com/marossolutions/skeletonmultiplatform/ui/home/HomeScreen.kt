package com.marossolutions.skeletonmultiplatform.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.datetime.TimeZone
import com.marossolutions.skeletonmultiplatform.model.Airport
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.marossolutions.skeletonmultiplatform.theme.AppTheme
import com.marossolutions.skeletonmultiplatform.ui.FullScreenLoading
import com.marossolutions.skeletonmultiplatform.ui.dialog.ErrorDialog
import com.marossolutions.skeletonmultiplatform.ui.home.HomeViewModel.HomeUiState
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import skeletonmultiplatform.composeapp.generated.resources.Res
import skeletonmultiplatform.composeapp.generated.resources.home_exit_button_text

@OptIn(KoinExperimentalAPI::class)
@Composable
internal fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.clearSelectedAirport()
    }

    HomeScreenContent(
        uiState = uiState,
        onAirportSelected = viewModel::navigateToAirportDetail,
        exitFromApplication = viewModel::exitFromApplication
    )
}

@Composable
private fun HomeScreenContent(
    uiState: HomeUiState,
    onAirportSelected: (String) -> Unit,
    exitFromApplication: () -> Unit,
) {
    when (uiState) {
        is HomeUiState.Content -> Content(
            airports = uiState.airports,
            onAirportSelected = onAirportSelected,
            exitFromApplication = exitFromApplication
        )

        HomeUiState.Error -> Box {
            FullScreenLoading()
            var isDialogOpen by remember { mutableStateOf(true) }
            if (isDialogOpen) {
                ErrorDialog {
                    isDialogOpen = false
                }
            }
        }

        HomeUiState.Loading -> FullScreenLoading()
    }
}

@Composable
private fun Content(
    airports: List<Airport>,
    onAirportSelected: (String) -> Unit,
    exitFromApplication: () -> Unit,
) {
    Box {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(airports) { index, airport ->
                Column {
                    AirportItem(
                        airport = airport,
                        onAirportClick = { onAirportSelected(airport.icao) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (index == airports.lastIndex) {
                        Spacer(Modifier.height(64.dp))
                    }
                }
            }
        }
        FloatingActionButton(
            modifier = Modifier.align(Alignment.BottomCenter)
                .padding(bottom = 12.dp),
            onClick = exitFromApplication
        ) {
            Text(stringResource(Res.string.home_exit_button_text))
        }
    }
}

@Preview
@Composable
private fun HomeScreenContentPreview() {
    AppTheme {
        HomeScreenContent(
            uiState = HomeUiState.Content(
                airports = listOf(
                    Airport(
                        icao = "LHBP",
                        name = "Ferihegy",
                        city = "Budapest",
                        latitude = "0.0",
                        longitude = "0.0",
                        timeZone = TimeZone.UTC,
                    )
                )
            ),
            onAirportSelected = { },
            exitFromApplication = { },
        )
    }
}