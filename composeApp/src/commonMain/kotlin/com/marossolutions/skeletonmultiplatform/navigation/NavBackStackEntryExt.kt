package com.marossolutions.skeletonmultiplatform.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.toRoute
import com.marossolutions.skeletonmultiplatform.navigation.screens.ScreenDetail
import com.marossolutions.skeletonmultiplatform.navigation.screens.ScreenHome
import com.marossolutions.skeletonmultiplatform.navigation.screens.ScreenInfo
import com.marossolutions.skeletonmultiplatform.navigation.screens.ScreenWelcome

/**
 * This solution needs to be replaced, when toRoute method will be able to return the screen
 * from current route of NavBackStackEntry destination
 * https://stackoverflow.com/a/78495523
 */
fun NavBackStackEntry.toAppScreen() = destination.route?.let { route ->
    when (route.substringBefore("?").substringBefore("/").substringAfterLast(".")) {
        ScreenWelcome::class.simpleName -> toRoute<ScreenWelcome>()
        ScreenInfo::class.simpleName -> toRoute<ScreenInfo>()
        ScreenHome::class.simpleName -> toRoute<ScreenHome>()
        ScreenDetail::class.simpleName -> toRoute<ScreenDetail>()
        else -> error("Route: $route, is not recognized")
    }
}
