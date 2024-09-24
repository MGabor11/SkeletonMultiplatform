package com.marossolutions.skeletonmultiplatform.network

interface AirportRemoteService {

    suspend fun getAirports(countryCode: String): List<AirportResponse>

    suspend fun getAirportByICAO(icao: String): AirportResponse
}