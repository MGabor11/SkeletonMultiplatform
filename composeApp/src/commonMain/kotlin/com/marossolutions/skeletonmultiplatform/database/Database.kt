package com.marossolutions.skeletonmultiplatform.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.CoroutineDispatcher

@Database(entities = [AirportEntity::class, AirlineEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getAirportDao(): AirportDao
    abstract fun getAirlineDao(): AirlineDao
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<AppDatabase>,
    dispatcher: CoroutineDispatcher,
): AppDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(dispatcher)
        .build()
}

internal const val dataBaseFileName = "my_room.db"
