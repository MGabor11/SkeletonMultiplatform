package com.marossolutions.skeletonmultiplatform.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface AirlineDao {

    @Upsert
    suspend fun upsert(item: AirlineEntity)

    @Upsert
    suspend fun upsert(items: List<AirlineEntity>)

    @Query("DELETE FROM airline")
    suspend fun deleteAll()

    @Query("SELECT * FROM airline")
    fun getAllAirlines(): Flow<List<AirlineEntity>>

    @Query("SELECT * FROM airline WHERE icao = :icao")
    fun getAirline(icao: String): Flow<AirlineEntity?>
}
