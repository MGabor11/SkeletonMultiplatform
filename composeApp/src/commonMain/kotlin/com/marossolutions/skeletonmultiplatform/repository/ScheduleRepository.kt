package com.marossolutions.skeletonmultiplatform.repository

import kotlinx.coroutines.flow.StateFlow
import com.marossolutions.skeletonmultiplatform.model.Schedule

interface ScheduleRepository {

    val airportSchedules : StateFlow<List<Schedule>?>

    suspend fun fetchAirportSchedule(icao: String)
}