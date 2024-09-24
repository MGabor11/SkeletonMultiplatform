package com.marossolutions.skeletonmultiplatform.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.Instant
import com.marossolutions.skeletonmultiplatform.model.Schedule
import com.marossolutions.skeletonmultiplatform.network.ScheduleRemoteService
import com.marossolutions.skeletonmultiplatform.service.AirlineService
import com.marossolutions.skeletonmultiplatform.service.AirportService

class ScheduleRepositoryImpl(
    private val scheduleRemoteService: ScheduleRemoteService,
    private val airportService: AirportService,
    private val airlineService: AirlineService,
) : ScheduleRepository {

    private val _airportSchedules = MutableStateFlow<List<Schedule>>(emptyList())

    override val airportSchedules: StateFlow<List<Schedule>?> = _airportSchedules.asStateFlow()

    override suspend fun fetchAirportSchedule(icao: String) {
        val schedules = scheduleRemoteService.getDepartureSchedule(icao).schedules
        val airports = airportService.getAirportsByIcaos(
            (schedules.map { it.departureAirportIcao } + schedules.map { it.arrivalAirportIcao })
                .toSet()
        )
        val airlines = airlineService.getAirlinesByIcaos(schedules.map { it.airlineIcao }.toSet())
        _airportSchedules.value = schedules.map { schedule ->
            Schedule(
                airlineIcao = schedule.airlineIcao,
                flightIcao = schedule.flightIcao,
                departureAirportIcao = schedule.departureAirportIcao,
                departureTerminal = schedule.departureTerminal,
                departureGate = schedule.departureGate,
                arrivalAirportIcao = schedule.arrivalAirportIcao,
                arrivalTerminal = schedule.arrivalTerminal,
                arrivalGate = schedule.arrivalGate,
                flightStatus = schedule.flightStatus,
                arrivalTime = Instant.fromEpochSeconds(schedule.arrivalTimeTs),
                departureTime = Instant.fromEpochSeconds(schedule.departureTimeTs),
                actualArrivalTime = schedule.actualArrivalTimeTs?.let { Instant.fromEpochSeconds(it) },
                actualDepartureTime = schedule.actualDepartureTimeTs?.let { Instant.fromEpochSeconds(it) },
                estimatedArrivalTime =  schedule.estimatedArrivalTime?.let { Instant.fromEpochSeconds(it) },
                estimatedDepartureTime = schedule.estimatedDepartureTime?.let { Instant.fromEpochSeconds(it) },
                arrivalAirport = airports.first { schedule.arrivalAirportIcao == it.icao },
                departureAirport = airports.first { schedule.departureAirportIcao == it.icao },
                airline = airlines.firstOrNull { schedule.airlineIcao == it.icao },
            )
        }
    }
}