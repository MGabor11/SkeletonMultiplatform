package com.marossolutions.skeletonmultiplatform.service

import com.marossolutions.skeletonmultiplatform.model.Airline

interface AirlineService {

    suspend fun getAirlineByIcao(airlineIcao: String) : Airline?

    suspend fun getAirlinesByIcaos(airlineIcaos: Set<String>) : List<Airline>
}