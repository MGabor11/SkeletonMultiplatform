package com.marossolutions.skeletonmultiplatform.network

import com.marossolutions.skeletonmultiplatform.network.ScheduleListResponse

interface ScheduleRemoteService {

    suspend fun getDepartureSchedule(icao: String): ScheduleListResponse

    suspend fun getArrivalSchedule(icao: String): ScheduleListResponse
}