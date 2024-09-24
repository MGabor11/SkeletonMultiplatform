package com.marossolutions.skeletonmultiplatform.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AirportResponse(
    @SerialName("icao") val icao: String,
    @SerialName("name") val name: String,
    @SerialName("country") val country: String,
    @SerialName("city") val city: String,
    @SerialName("latitude") val latitude: String,
    @SerialName("longitude") val longitude: String,
    @SerialName("timezone") val timezone: String,
)
