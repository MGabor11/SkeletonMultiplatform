package com.marossolutions.skeletonmultiplatform.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.kamel.core.Resource
import io.kamel.image.asyncPainterResource
import kotlinx.datetime.TimeZone
import com.marossolutions.skeletonmultiplatform.model.Airport
import com.marossolutions.skeletonmultiplatform.model.Schedule
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import com.marossolutions.skeletonmultiplatform.theme.AppTheme
import com.marossolutions.skeletonmultiplatform.ui.FullScreenLoading
import com.marossolutions.skeletonmultiplatform.ui.detail.DetailViewModel.DetailUiState
import org.koin.core.annotation.KoinExperimentalAPI
import skeletonmultiplatform.composeapp.generated.resources.Res
import skeletonmultiplatform.composeapp.generated.resources.detail_departures_subtitle

@OptIn(KoinExperimentalAPI::class)
@Composable
internal fun DetailScreen(viewModel: DetailViewModel = koinViewModel()) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DetailScreenContent(uiState)
}

@Composable
private fun DetailScreenContent(uiState: DetailUiState) {
    if (uiState is DetailUiState.Loading) {
        FullScreenLoading()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            val airport = (uiState as DetailUiState.Content).airport
            Text(
                text = airport.name,
                fontSize = 18.sp,
            )

            Text(airport.city, fontSize = 16.sp)

            AirportMap(airport = airport, mapsKey = uiState.mapsKey)

            Text(stringResource(Res.string.detail_departures_subtitle), fontSize = 16.sp)

            ScheduleList(uiState.schedules)
        }
    }
}

@Composable
private fun AirportMap(airport: Airport, mapsKey: String) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .height(200.dp)
            .background(color = Color.DarkGray)
    ) {
        when (val resource =
            asyncPainterResource(
                MAP_BASE_URL +
                    "${airport.longitude},${airport.latitude}&zoom=$MAP_ZOOM_LEVEL&apiKey=" + mapsKey
            )) {
            is Resource.Failure,
            is Resource.Loading,
            -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

            is Resource.Success -> Image(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillWidth,
                painter = resource.value,
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun ScheduleList(schedules: List<Schedule>) {
    LazyColumn {
        itemsIndexed(schedules) { index, item ->
            ScheduleItem(item)
            if (index < schedules.lastIndex) {
                HorizontalDivider()
            }
        }
    }
}

private const val MAP_BASE_URL =
    "https://maps.geoapify.com/v1/staticmap?style=osm-carto&width=600&height=400&center=lonlat:"

private const val MAP_ZOOM_LEVEL = 12.0f

@Preview
@Composable
private fun DetailScreenContentPreview() {
    AppTheme {
        DetailScreenContent(
            uiState = DetailUiState.Content(
                airport = Airport(
                    icao = "LHBP",
                    name = "Ferihegy",
                    city = "Budapest",
                    latitude = "0.0",
                    longitude = "0.0",
                    timeZone = TimeZone.UTC
                ),
                mapsKey = "",
                schedules = emptyList()
            )
        )
    }
}