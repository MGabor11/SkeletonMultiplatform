package com.marossolutions.skeletonmultiplatform.service

import com.marossolutions.skeletonmultiplatform.model.Airport

interface AirportService {

    suspend fun getAirportByIcao(icao: String): Airport

    suspend fun getAirportsByIcaos(icaos: Set<String>): List<Airport>
}