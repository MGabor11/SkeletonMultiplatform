package com.marossolutions.skeletonmultiplatform.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.withContext
import com.marossolutions.skeletonmultiplatform.service.DispatcherProvider

class AirlineRemoteServiceImpl(
    private val httpClient: HttpClient,
    private val dispatcherProvider: DispatcherProvider,
    private val apiNinjaBaseUrl: String,
) : AirlineRemoteService {

    override suspend fun getAirline(icao: String): AirlineResponse? = withContext(dispatcherProvider.io) {
        val response = httpClient.get(apiNinjaBaseUrl + "airlines?icao=" + icao).body<List<AirlineResponse>>()
        return@withContext response.firstOrNull()
    }
}