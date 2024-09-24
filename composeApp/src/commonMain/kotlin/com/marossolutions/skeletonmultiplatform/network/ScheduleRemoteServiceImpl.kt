package com.marossolutions.skeletonmultiplatform.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.withContext
import com.marossolutions.skeletonmultiplatform.service.DispatcherProvider

class ScheduleRemoteServiceImpl(
    private val httpClient: HttpClient,
    private val dispatcherProvider: DispatcherProvider,
    private val airlabsBaseUrl: String,
) : ScheduleRemoteService {

    override suspend fun getDepartureSchedule(icao: String): ScheduleListResponse = withContext(dispatcherProvider.io) {
        return@withContext httpClient.get(airlabsBaseUrl + "schedules?dep_icao=$icao")
            .body<ScheduleListResponse>()
    }

    override suspend fun getArrivalSchedule(icao: String): ScheduleListResponse = withContext(dispatcherProvider.io) {
        return@withContext httpClient.get(airlabsBaseUrl + "schedules?&arr_icao=$icao$icao")
            .body<ScheduleListResponse>()
    }
}
