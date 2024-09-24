package com.marossolutions.skeletonmultiplatform.repository

import com.marossolutions.skeletonmultiplatform.database.AirportDao
import com.marossolutions.skeletonmultiplatform.database.AirportEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.datetime.TimeZone
import com.marossolutions.skeletonmultiplatform.model.Airport
import com.marossolutions.skeletonmultiplatform.network.AirportRemoteService

class AirportRepositoryImpl(
    private val airportRemoteService: AirportRemoteService,
    private val airportDao: AirportDao,
) : AirportRepository {

    private val _airports = MutableStateFlow<List<Airport>>(emptyList())

    private val _selectedAirportDetail = MutableStateFlow<Airport?>(null)

    private val _airportFetchError = MutableStateFlow(false)

    override val airports: StateFlow<List<Airport>> = _airports.asStateFlow()
    override val airportDetail: StateFlow<Airport?> = _selectedAirportDetail.asStateFlow()
    override val isErrorOccurred: StateFlow<Boolean> = _airportFetchError.asStateFlow()

    override suspend fun fetchAirports() {
        try {
            _airportFetchError.value = false

            val airports = airportDao.getAllAirport().firstOrNull()
                .takeUnless { it.isNullOrEmpty() } ?: airportRemoteService.getAirports(
                HUNGARIAN_COUNTRY_CODE
            )
                .let { airports ->
                    airportDao.upsert(
                        airports.map { airport ->
                            AirportEntity(
                                icao = airport.icao,
                                name = airport.name,
                                city = airport.city,
                                latitude = airport.latitude,
                                longitude = airport.longitude,
                                timezone = airport.timezone,
                                country = airport.country,
                            )
                        }
                    )
                    airportDao.getAllAirport().first()
                }

            _airports.value = airports.map { airportDataModel ->
                Airport(
                    icao = airportDataModel.icao,
                    name = airportDataModel.name,
                    city = airportDataModel.city,
                    latitude = airportDataModel.latitude,
                    longitude = airportDataModel.longitude,
                    timeZone = TimeZone.of(airportDataModel.timezone),
                )
            }
        } catch (exception: Exception) {
            _airportFetchError.value = true
        }
    }

    override suspend fun refreshAirports() {
        airportDao.deleteAll()
        fetchAirports()
    }

    override suspend fun fetchAirportDetails(icao: String) {
        try {
            _airportFetchError.value = false
            val airportResponse = airportRemoteService.getAirportByICAO(icao)
            _selectedAirportDetail.value = airportResponse.let { response ->
                Airport(
                    icao = response.icao,
                    name = response.name,
                    city = response.city,
                    latitude = response.latitude,
                    longitude = response.longitude,
                    timeZone = TimeZone.of(response.timezone)
                )
            }
        } catch (exception: Exception) {
            _airportFetchError.value = true
        }
    }

    override fun clearSelectedAirport() {
        _selectedAirportDetail.value = null
    }

    companion object {
        private const val HUNGARIAN_COUNTRY_CODE = "HU"
    }
}
