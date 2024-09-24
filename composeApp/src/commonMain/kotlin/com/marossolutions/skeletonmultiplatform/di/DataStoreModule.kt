package com.marossolutions.skeletonmultiplatform.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.marossolutions.skeletonmultiplatform.datastore.AppInfoPreferences
import com.marossolutions.skeletonmultiplatform.datastore.AppInfoPreferencesImpl
import com.marossolutions.skeletonmultiplatform.datastore.createDataStore
import com.marossolutions.skeletonmultiplatform.di.qualifier.dataStorePath
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataStoreModule = module {
    single<DataStore<Preferences>> { createDataStore { get<String>(qualifier = dataStorePath) } }
    singleOf(::AppInfoPreferencesImpl).bind<AppInfoPreferences>()
}