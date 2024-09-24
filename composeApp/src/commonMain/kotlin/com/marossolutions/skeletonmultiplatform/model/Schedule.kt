package com.marossolutions.skeletonmultiplatform.model

import com.marossolutions.skeletonmultiplatform.model.Airline
import com.marossolutions.skeletonmultiplatform.model.Airport
import kotlinx.datetime.Instant

data class Schedule(
    val airlineIcao: String,
    val flightIcao: String,
    val departureAirportIcao: String,
    val departureTerminal: String?,
    val departureGate: String?,
    val arrivalAirportIcao: String,
    val arrivalTerminal: String?,
    val arrivalGate: String?,
    val flightStatus: String,
    val arrivalTime: Instant,
    val departureTime: Instant,
    val actualArrivalTime: Instant?,
    val estimatedArrivalTime: Instant?,
    val actualDepartureTime: Instant?,
    val estimatedDepartureTime: Instant?,
    val arrivalAirport: Airport,
    val departureAirport: Airport,
    val airline: Airline?
)