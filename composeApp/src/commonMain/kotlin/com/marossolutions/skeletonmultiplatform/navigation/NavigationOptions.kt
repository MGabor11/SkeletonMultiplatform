package com.marossolutions.skeletonmultiplatform.navigation

import com.marossolutions.skeletonmultiplatform.navigation.screens.AppScreen

data class NavigationOptions(
    val popUpToScreen: AppScreen,
    val popUpToInclusive: Boolean
)
