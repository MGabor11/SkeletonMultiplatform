package com.marossolutions.skeletonmultiplatform.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.marossolutions.skeletonmultiplatform.database.AppDatabase
import com.marossolutions.skeletonmultiplatform.database.dataBaseFileName
import com.marossolutions.skeletonmultiplatform.datastore.dataStoreFileName
import com.marossolutions.skeletonmultiplatform.di.qualifier.dataStorePath
import com.marossolutions.skeletonmultiplatform.di.qualifier.nativeDataBaseBuilder
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
import com.marossolutions.skeletonmultiplatform.service.ApplicationCloseService
import com.marossolutions.skeletonmultiplatform.service.IOSApplicationCloseService

actual val platformModule = module {
    singleOf(::IOSApplicationCloseService).bind<ApplicationCloseService>()

    single<String>(qualifier = dataStorePath) {
        documentDirectoryPath() + "/$dataStoreFileName"
    }
    single<RoomDatabase.Builder<AppDatabase>>(qualifier = nativeDataBaseBuilder) {
        val dbFilePath = documentDirectoryPath() + "/$dataBaseFileName"
        Room.databaseBuilder<AppDatabase>(
            name = dbFilePath,
        )
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): NSURL? = NSFileManager.defaultManager.URLForDirectory(
    directory = NSDocumentDirectory,
    inDomain = NSUserDomainMask,
    appropriateForURL = null,
    create = false,
    error = null,
)

private fun documentDirectoryPath() = requireNotNull(documentDirectory()).path
