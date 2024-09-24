package com.marossolutions.skeletonmultiplatform.di

import com.marossolutions.skeletonmultiplatform.di.qualifier.airlabsBaseUrl
import com.marossolutions.skeletonmultiplatform.di.qualifier.apiNinjaBaseUrl
import com.marossolutions.skeletonmultiplatform.network.AirlineRemoteService
import com.marossolutions.skeletonmultiplatform.network.AirlineRemoteServiceImpl
import com.marossolutions.skeletonmultiplatform.network.AirportRemoteService
import com.marossolutions.skeletonmultiplatform.network.AirportRemoteServiceImpl
import com.marossolutions.skeletonmultiplatform.network.ScheduleRemoteService
import com.marossolutions.skeletonmultiplatform.network.ScheduleRemoteServiceImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import com.marossolutions.skeletonmultiplatform.service.AirlineService
import com.marossolutions.skeletonmultiplatform.service.AirportService
import com.marossolutions.skeletonmultiplatform.service.DispatcherProvider
import com.marossolutions.skeletonmultiplatform.service.DispatcherProviderImpl
import com.marossolutions.skeletonmultiplatform.service.AirportServiceImpl
import com.marossolutions.skeletonmultiplatform.service.AirlineServiceImpl

val serviceModule = module {
    singleOf(::DispatcherProviderImpl).bind<DispatcherProvider>()
    single<AirportRemoteService> {
        AirportRemoteServiceImpl(
            httpClient = get(),
            dispatcherProvider = get(),
            apiNinjaBaseUrl = get<String>(qualifier = apiNinjaBaseUrl),
        )
    }
    single<AirlineRemoteService> {
        AirlineRemoteServiceImpl(
            httpClient = get(),
            dispatcherProvider = get(),
            apiNinjaBaseUrl = get<String>(qualifier = apiNinjaBaseUrl),
        )
    }
    single<ScheduleRemoteService> {
        ScheduleRemoteServiceImpl(
            httpClient = get(),
            dispatcherProvider = get(),
            airlabsBaseUrl = get<String>(qualifier = airlabsBaseUrl),
        )
    }
    singleOf(::AirportServiceImpl).bind<AirportService>()
    singleOf(::AirlineServiceImpl).bind<AirlineService>()
}