package com.marossolutions.skeletonmultiplatform.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScheduleListResponse(
    @SerialName("response") val schedules: List<ScheduleResponse>,
)

@Serializable
data class ScheduleResponse(
    @SerialName("airline_iata") val airlineIata: String?,
    @SerialName("airline_icao") val airlineIcao: String,
    @SerialName("flight_iata") val flightIata: String?,
    @SerialName("flight_icao") val flightIcao: String,
    @SerialName("dep_icao") val departureAirportIcao: String,
    @SerialName("dep_terminal") val departureTerminal: String?,
    @SerialName("dep_gate") val departureGate: String?,
    @SerialName("dep_time") val departureTime: String,
    @SerialName("dep_time_utc") val departureTimeUtc: String,
    @SerialName("dep_actual") val departureActualTime: String?,
    @SerialName("dep_actual_utc") val departureActualTimeUtc: String?,
    @SerialName("arr_icao") val arrivalAirportIcao: String,
    @SerialName("arr_terminal") val arrivalTerminal: String?,
    @SerialName("arr_gate") val arrivalGate: String?,
    @SerialName("arr_time") val arrivalTime: String,
    @SerialName("arr_time_utc") val arrivalTimeUtc: String,
    @SerialName("arr_actual") val arrivalActualTime: String?,
    @SerialName("arr_actual_utc") val arrivalActualTimeUtc: String?,
    @SerialName("status") val flightStatus: String,
    @SerialName("dep_time_ts") val departureTimeTs: Long,
    @SerialName("arr_time_ts") val arrivalTimeTs: Long,
    @SerialName("arr_actual_ts") val actualArrivalTimeTs: Long?,
    @SerialName("dep_actual_ts") val actualDepartureTimeTs: Long?,
    @SerialName("arr_estimated_ts") val estimatedArrivalTime: Long?,
    @SerialName("dep_estimated_ts") val estimatedDepartureTime: Long?,
)
