package com.marossolutions.skeletonmultiplatform.network

interface AirlineRemoteService {

    suspend fun getAirline(icao: String): AirlineResponse?
}