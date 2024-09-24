package com.marossolutions.skeletonmultiplatform.di

import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    includes(
        coreModule,
        httpClientModule,
        dataBaseModule,
        dataStoreModule,
        serviceModule,
        repositoryModule,
        viewModelModule,
        navigationModule,
    )
}
