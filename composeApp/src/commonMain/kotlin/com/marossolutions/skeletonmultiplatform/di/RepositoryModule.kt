package com.marossolutions.skeletonmultiplatform.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import com.marossolutions.skeletonmultiplatform.repository.AirportRepository
import com.marossolutions.skeletonmultiplatform.repository.AirportRepositoryImpl
import com.marossolutions.skeletonmultiplatform.repository.ScheduleRepositoryImpl
import com.marossolutions.skeletonmultiplatform.repository.ScheduleRepository

val repositoryModule = module {
    singleOf(::AirportRepositoryImpl).bind<AirportRepository>()
    singleOf(::ScheduleRepositoryImpl).bind<ScheduleRepository>()
}