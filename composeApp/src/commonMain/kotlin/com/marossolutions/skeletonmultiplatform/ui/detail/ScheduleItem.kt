package com.marossolutions.skeletonmultiplatform.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import io.kamel.core.Resource
import io.kamel.image.asyncPainterResource
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.until
import com.marossolutions.skeletonmultiplatform.model.Schedule
import org.jetbrains.compose.resources.painterResource
import skeletonmultiplatform.composeapp.generated.resources.Res
import skeletonmultiplatform.composeapp.generated.resources.error_icon

@Composable
fun ScheduleItem(schedule: Schedule) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Row {
            Box(
                modifier = Modifier.size(64.dp)
            ) {
                schedule.airline?.logoUrl?.let { airlineLogoUrl ->
                    when (val resource =
                        asyncPainterResource(airlineLogoUrl)) {
                        is Resource.Failure -> Image(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(Res.drawable.error_icon),
                            contentDescription = null,
                        )

                        is Resource.Loading,
                        -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

                        is Resource.Success -> Image(
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.FillWidth,
                            painter = resource.value,
                            contentDescription = null,
                        )
                    }
                } ?: Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(Res.drawable.error_icon),
                    contentDescription = null,
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(text = schedule.departureAirport.name)
                Text(
                    text = "Official: " + schedule.departureTime.toLocalDateTime(schedule.departureAirport.timeZone)
                        .format(
                            LocalDateTime.Format {
                                dayOfMonth()
                                char('/')
                                monthNumber()
                                char(' ')
                                hour()
                                char(':')
                                minute()
                            }
                        ),
                    color = if (
                        (schedule.actualDepartureTime ?: schedule.estimatedDepartureTime)?.let { actualDepartureTime ->
                            schedule.departureTime.until(
                                actualDepartureTime,
                                DateTimeUnit.MINUTE
                            ) > MAX_DELAY_IN_MINUTES
                        } == true) {
                        Color.Red
                    } else {
                        Color.Unspecified
                    }
                )

                schedule.actualDepartureTime?.let { actualDepartureTime ->
                    Text(
                        text = "Actual: " + actualDepartureTime.toLocalDateTime(schedule.departureAirport.timeZone)
                            .format(
                                LocalDateTime.Format {
                                    dayOfMonth()
                                    char('/')
                                    monthNumber()
                                    char(' ')
                                    hour()
                                    char(':')
                                    minute()
                                }
                            )
                    )
                }

                if (schedule.actualDepartureTime == null) {
                    schedule.estimatedDepartureTime?.let { actualDepartureTime ->
                        Text(
                            text = "Estimated: " + actualDepartureTime.toLocalDateTime(schedule.departureAirport.timeZone)
                                .format(
                                    LocalDateTime.Format {
                                        dayOfMonth()
                                        char('/')
                                        monthNumber()
                                        char(' ')
                                        hour()
                                        char(':')
                                        minute()
                                    }
                                )
                        )
                    }
                }

                Text(text = schedule.arrivalAirport.name)

                Text(
                    text = schedule.arrivalTime.toLocalDateTime(schedule.arrivalAirport.timeZone).format(
                        LocalDateTime.Format {
                            dayOfMonth()
                            char('/')
                            monthNumber()
                            char(' ')
                            hour()
                            char(':')
                            minute()
                        }
                    )
                )
            }
        }

        Text("Status: " + schedule.flightStatus)
    }
}

const val MAX_DELAY_IN_MINUTES = 15