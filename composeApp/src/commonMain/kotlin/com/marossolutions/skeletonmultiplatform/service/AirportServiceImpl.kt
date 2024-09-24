package com.marossolutions.skeletonmultiplatform.service

import com.marossolutions.skeletonmultiplatform.database.AirportDao
import com.marossolutions.skeletonmultiplatform.database.AirportEntity
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.datetime.TimeZone
import com.marossolutions.skeletonmultiplatform.model.Airport
import com.marossolutions.skeletonmultiplatform.network.AirportRemoteService
import com.marossolutions.skeletonmultiplatform.network.AirportResponse

class AirportServiceImpl(
    private val airportDao: AirportDao,
    private val airportRemoteService: AirportRemoteService,
) : AirportService {

    override suspend fun getAirportByIcao(icao: String): Airport {
        val result = airportDao.getAirport(icao).firstOrNull()
        return if (result == null) {
            fetchAirport(icao)
            checkNotNull(airportDao.getAirport(icao).firstOrNull())
        } else {
            result
        }.toAirport()
    }

    override suspend fun getAirportsByIcaos(icaos: Set<String>): List<Airport> = coroutineScope {
        icaos.map {
            async { getAirportByIcao(it) }
        }.awaitAll()
    }

    private suspend fun fetchAirport(icao: String) {
        val response = airportRemoteService.getAirportByICAO(icao)
        airportDao.upsert(response.toAirportEntity())
    }

    private fun AirportResponse.toAirportEntity() = AirportEntity(
        icao = this.icao,
        name = this.name,
        country = this.country,
        city = this.city,
        latitude = this.latitude,
        longitude = this.longitude,
        timezone = this.timezone
    )

    private fun AirportEntity.toAirport() = Airport(
        icao = this.icao,
        name = this.name,
        city = this.city,
        latitude = this.latitude,
        longitude = this.longitude,
        timeZone = TimeZone.of(this.timezone)
    )
}
