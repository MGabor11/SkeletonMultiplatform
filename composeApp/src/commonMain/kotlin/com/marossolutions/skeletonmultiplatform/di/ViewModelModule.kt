package com.marossolutions.skeletonmultiplatform.di

import com.marossolutions.skeletonmultiplatform.di.qualifier.geoapifyMapsKey
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import com.marossolutions.skeletonmultiplatform.ui.detail.DetailViewModel
import com.marossolutions.skeletonmultiplatform.ui.home.HomeViewModel
import com.marossolutions.skeletonmultiplatform.ui.welcome.InfoViewModel
import com.marossolutions.skeletonmultiplatform.ui.welcome.WelcomeViewModel

val viewModelModule = module {
    viewModelOf(::WelcomeViewModel)
    viewModelOf(::InfoViewModel)
    viewModelOf(::HomeViewModel)
    viewModel { DetailViewModel(get(), get(), get(), get(qualifier = geoapifyMapsKey)) }
}