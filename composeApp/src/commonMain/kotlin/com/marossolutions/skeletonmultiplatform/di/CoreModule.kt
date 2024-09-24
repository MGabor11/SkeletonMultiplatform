package com.marossolutions.skeletonmultiplatform.di

import com.marossolutions.skeletonmultiplatform.BuildKonfig
import com.marossolutions.skeletonmultiplatform.di.qualifier.airlabsApiKey
import com.marossolutions.skeletonmultiplatform.di.qualifier.airlabsBaseUrl
import com.marossolutions.skeletonmultiplatform.di.qualifier.apiNinjaApiKey
import com.marossolutions.skeletonmultiplatform.di.qualifier.apiNinjaBaseUrl
import com.marossolutions.skeletonmultiplatform.di.qualifier.geoapifyMapsKey
import org.koin.dsl.module

val coreModule = module {
    single<String>(qualifier = apiNinjaApiKey) { BuildKonfig.API_NINJA_API_KEY }
    single<String>(qualifier = airlabsApiKey) { BuildKonfig.AIRLABS_API_KEY }
    single<String>(qualifier = geoapifyMapsKey) { BuildKonfig.GEOAPIFY_MAPS_KEY }
    single<String>(qualifier = apiNinjaBaseUrl) { BuildKonfig.API_NINJA_BASE_URL }
    single<String>(qualifier = airlabsBaseUrl) { BuildKonfig.AIRLABS_BASE_URL }
}