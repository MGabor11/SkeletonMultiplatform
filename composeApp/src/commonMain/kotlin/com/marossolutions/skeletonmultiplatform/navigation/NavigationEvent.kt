package com.marossolutions.skeletonmultiplatform.navigation

import com.marossolutions.skeletonmultiplatform.navigation.screens.AppScreen

sealed interface NavigationEvent {

    data object NavigateUp : NavigationEvent

    data class ForwardNavigation(
        val screen: AppScreen,
        val navigationOptions: NavigationOptions? = null
    ) : NavigationEvent
}
