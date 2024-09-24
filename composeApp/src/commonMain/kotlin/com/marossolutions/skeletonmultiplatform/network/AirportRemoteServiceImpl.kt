package com.marossolutions.skeletonmultiplatform.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.withContext
import com.marossolutions.skeletonmultiplatform.service.DispatcherProvider

class AirportRemoteServiceImpl(
    private val httpClient: HttpClient,
    private val dispatcherProvider: DispatcherProvider,
    private val apiNinjaBaseUrl: String,
) : AirportRemoteService {

    override suspend fun getAirports(countryCode: String): List<AirportResponse> = withContext(dispatcherProvider.io) {
        return@withContext httpClient.get(apiNinjaBaseUrl + "airports?country=" + countryCode)
            .body<List<AirportResponse>>()
    }

    override suspend fun getAirportByICAO(icao: String): AirportResponse = withContext(dispatcherProvider.io) {
        val response = httpClient.get(apiNinjaBaseUrl + "airports?icao=" + icao).body<List<AirportResponse>>()
        return@withContext response.first()
    }
}