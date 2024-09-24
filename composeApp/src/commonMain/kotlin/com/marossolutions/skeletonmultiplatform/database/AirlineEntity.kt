package com.marossolutions.skeletonmultiplatform.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "airline")
data class AirlineEntity(
    @PrimaryKey val icao: String,
    val name: String,
    val logoUrl: String?
)