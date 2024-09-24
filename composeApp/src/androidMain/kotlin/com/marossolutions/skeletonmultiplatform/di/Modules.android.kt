package com.marossolutions.skeletonmultiplatform.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.marossolutions.skeletonmultiplatform.database.AppDatabase
import com.marossolutions.skeletonmultiplatform.database.dataBaseFileName
import com.marossolutions.skeletonmultiplatform.datastore.dataStoreFileName
import com.marossolutions.skeletonmultiplatform.di.qualifier.dataStorePath
import com.marossolutions.skeletonmultiplatform.di.qualifier.nativeDataBaseBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.binds
import org.koin.dsl.module
import com.marossolutions.skeletonmultiplatform.service.AndroidApplicationCloseService
import com.marossolutions.skeletonmultiplatform.service.AndroidApplicationCloseServiceImpl
import com.marossolutions.skeletonmultiplatform.service.ApplicationCloseService

actual val platformModule = module {
    singleOf(::AndroidApplicationCloseServiceImpl).binds(
        arrayOf(
            AndroidApplicationCloseService::class,
            ApplicationCloseService::class
        )
    )

    single<String>(qualifier = dataStorePath) {
        androidContext().filesDir
            .resolve(dataStoreFileName)
            .absolutePath
    }

    single<RoomDatabase.Builder<AppDatabase>>(qualifier = nativeDataBaseBuilder) {
        val appContext = androidContext().applicationContext
        val dbFile = appContext.getDatabasePath(dataBaseFileName)
        Room.databaseBuilder<AppDatabase>(
            context = appContext,
            name = dbFile.absolutePath
        )
    }
}
