package com.marossolutions.skeletonmultiplatform.service

import com.marossolutions.skeletonmultiplatform.database.AirlineDao
import com.marossolutions.skeletonmultiplatform.database.AirlineEntity
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.firstOrNull
import com.marossolutions.skeletonmultiplatform.model.Airline
import com.marossolutions.skeletonmultiplatform.network.AirlineRemoteService
import com.marossolutions.skeletonmultiplatform.network.AirlineResponse

class AirlineServiceImpl(
    private val airlineRemoteService: AirlineRemoteService,
    private val airlineDao: AirlineDao,
) : AirlineService {

    override suspend fun getAirlineByIcao(airlineIcao: String): Airline? {
        val result = airlineDao.getAirline(airlineIcao).firstOrNull()
        return if (result == null) {
            fetchAirline(airlineIcao)
            airlineDao.getAirline(airlineIcao).firstOrNull()
        } else {
            result
        }?.toAirline()
    }

    override suspend fun getAirlinesByIcaos(airlineIcaos: Set<String>): List<Airline> = coroutineScope {
        airlineIcaos.map {
            async { getAirlineByIcao(it) }
        }.awaitAll()
            .filterNotNull()
    }

    private suspend fun fetchAirline(airlineIcao: String) {
        val response = airlineRemoteService.getAirline(airlineIcao)
        response?.let { airlineDao.upsert(it.toAirlineEntity()) }
    }

    private fun AirlineEntity.toAirline() = Airline(
        icao = this.icao,
        name = this.name,
        logoUrl = this.logoUrl
    )

    private fun AirlineResponse.toAirlineEntity() = AirlineEntity(
        icao = this.icao,
        name = this.name,
        logoUrl = this.logoUrl
    )
}
