package com.marossolutions.skeletonmultiplatform.repository

import kotlinx.coroutines.flow.StateFlow
import com.marossolutions.skeletonmultiplatform.model.Airport

interface AirportRepository {

    val airports: StateFlow<List<Airport>>

    val airportDetail: StateFlow<Airport?>

    val isErrorOccurred: StateFlow<Boolean>

    suspend fun fetchAirports()

    suspend fun refreshAirports()

    suspend fun fetchAirportDetails(icao: String)

    fun clearSelectedAirport()
}
