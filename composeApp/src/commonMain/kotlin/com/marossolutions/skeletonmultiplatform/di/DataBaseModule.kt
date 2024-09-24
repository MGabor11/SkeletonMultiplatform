package com.marossolutions.skeletonmultiplatform.di

import androidx.room.RoomDatabase
import com.marossolutions.skeletonmultiplatform.database.AirlineDao
import com.marossolutions.skeletonmultiplatform.database.AirportDao
import com.marossolutions.skeletonmultiplatform.database.AppDatabase
import com.marossolutions.skeletonmultiplatform.database.getRoomDatabase
import com.marossolutions.skeletonmultiplatform.di.qualifier.nativeDataBaseBuilder
import org.koin.dsl.module
import com.marossolutions.skeletonmultiplatform.service.DispatcherProvider

val dataBaseModule = module {
    single<AppDatabase> {
        getRoomDatabase(
            builder = get<RoomDatabase.Builder<AppDatabase>>(qualifier = nativeDataBaseBuilder),
            dispatcher = get<DispatcherProvider>().io
        )
    }
    single<AirportDao> { get<AppDatabase>().getAirportDao() }
    single<AirlineDao> { get<AppDatabase>().getAirlineDao() }
}