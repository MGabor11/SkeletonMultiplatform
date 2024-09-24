package com.marossolutions.skeletonmultiplatform.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import com.marossolutions.skeletonmultiplatform.navigation.screens.ScreenDetail
import com.marossolutions.skeletonmultiplatform.navigation.screens.ScreenHome
import com.marossolutions.skeletonmultiplatform.navigation.screens.ScreenInfo
import com.marossolutions.skeletonmultiplatform.navigation.screens.ScreenWelcome
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import com.marossolutions.skeletonmultiplatform.service.ApplicationCloseService
import com.marossolutions.skeletonmultiplatform.ui.detail.DetailScreen
import com.marossolutions.skeletonmultiplatform.ui.detail.DetailViewModel
import com.marossolutions.skeletonmultiplatform.ui.home.HomeScreen
import com.marossolutions.skeletonmultiplatform.ui.home.HomeViewModel
import com.marossolutions.skeletonmultiplatform.ui.welcome.InfoScreen
import com.marossolutions.skeletonmultiplatform.ui.welcome.InfoViewModel
import com.marossolutions.skeletonmultiplatform.ui.welcome.WelcomeScreen
import com.marossolutions.skeletonmultiplatform.ui.welcome.WelcomeViewModel

@OptIn(KoinExperimentalAPI::class)
@Composable
fun AppNavHost(
    simpleNavigator: SimpleNavigator,
    innerPadding: PaddingValues,
    applicationCloseService: ApplicationCloseService,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        modifier = Modifier.padding(innerPadding),
        startDestination = ScreenWelcome
    ) {
        composable<ScreenWelcome> {
            WelcomeScreen()
        }
        composable<ScreenInfo> {
            InfoScreen()
        }
        composable<ScreenHome> {
            HomeScreen()
        }
        composable<ScreenDetail> {
            DetailScreen()
        }
    }

    LaunchedEffect(Unit) {
        simpleNavigator.navigationEvents
            .onEach { navigateEvent ->
                when (navigateEvent) {
                    is NavigationEvent.ForwardNavigation -> navController.navigate(navigateEvent.screen) {
                        navigateEvent.navigationOptions?.let { navigationOptions ->
                            popUpTo(navigationOptions.popUpToScreen) {
                                inclusive = navigationOptions.popUpToInclusive
                            }
                        }
                    }

                    NavigationEvent.NavigateUp -> {
                        if (!navController.navigateUp()) {
                            applicationCloseService.closeApplication()
                        }
                    }

                }
            }
            .launchIn(this)

        navController.currentBackStackEntryFlow
            .onEach {
                simpleNavigator.setCurrentAppScreen(it.toAppScreen())
            }
            .launchIn(this)
    }
}
