package com.marossolutions.skeletonmultiplatform.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {

    @Upsert
    suspend fun upsert(item: AirportEntity)

    @Upsert
    suspend fun upsert(items: List<AirportEntity>)

    @Query("DELETE FROM airport")
    suspend fun deleteAll()

    @Query("SELECT * FROM airport")
    fun getAllAirport(): Flow<List<AirportEntity>>

    @Query("SELECT * FROM airport WHERE icao = :icao")
    fun getAirport(icao: String): Flow<AirportEntity?>

    @Query("SELECT * FROM airport WHERE icao = :icao")
    fun getAirportByCountry(icao: String): Flow<AirportEntity?>
}
