package com.marossolutions.skeletonmultiplatform.model

import kotlinx.datetime.TimeZone

data class Airport(
    val icao: String,
    val name: String,
    val city: String,
    val latitude: String,
    val longitude: String,
    val timeZone: TimeZone
)
