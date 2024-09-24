package com.marossolutions.skeletonmultiplatform.di

import com.marossolutions.skeletonmultiplatform.navigation.SimpleNavigator
import com.marossolutions.skeletonmultiplatform.navigation.SimpleNavigatorImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val navigationModule = module {
    singleOf(::SimpleNavigatorImpl).bind<SimpleNavigator>()
}