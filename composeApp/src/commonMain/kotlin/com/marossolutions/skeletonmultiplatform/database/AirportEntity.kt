package com.marossolutions.skeletonmultiplatform.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "airport")
data class AirportEntity(
    @PrimaryKey val icao: String,
    val name: String,
    val country: String,
    val city: String,
    val latitude: String,
    val longitude: String,
    val timezone: String,
)