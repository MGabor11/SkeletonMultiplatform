package com.marossolutions.skeletonmultiplatform.navigation

import com.marossolutions.skeletonmultiplatform.navigation.NavigationEvent
import com.marossolutions.skeletonmultiplatform.navigation.NavigationOptions
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import com.marossolutions.skeletonmultiplatform.navigation.screens.AppScreen

interface SimpleNavigator {

    val navigationEvents: SharedFlow<NavigationEvent>

    val currentAppScreen: StateFlow<AppScreen?>

    fun setCurrentAppScreen(screen: AppScreen?)

    fun navigateTo(screen: AppScreen, navigationOptions: NavigationOptions? = null)

    fun navigateUp()
}