package com.marossolutions.skeletonmultiplatform

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.marossolutions.skeletonmultiplatform.navigation.AppNavHost
import com.marossolutions.skeletonmultiplatform.navigation.SimpleNavigator
import com.marossolutions.skeletonmultiplatform.navigation.screens.ScreenDetail
import com.marossolutions.skeletonmultiplatform.navigation.screens.ScreenHome
import com.marossolutions.skeletonmultiplatform.navigation.screens.ScreenInfo
import com.marossolutions.skeletonmultiplatform.navigation.screens.ScreenWelcome
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import com.marossolutions.skeletonmultiplatform.service.ApplicationCloseService
import com.marossolutions.skeletonmultiplatform.theme.AppTheme
import skeletonmultiplatform.composeapp.generated.resources.Res
import skeletonmultiplatform.composeapp.generated.resources.detail_title
import skeletonmultiplatform.composeapp.generated.resources.home_title
import skeletonmultiplatform.composeapp.generated.resources.info_title
import skeletonmultiplatform.composeapp.generated.resources.welcome_title

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    AppTheme {
        KoinContext {
            val simpleNavigator = koinInject<SimpleNavigator>()
            val applicationCloseService = koinInject<ApplicationCloseService>()
            val navController: NavHostController = rememberNavController()
            Scaffold(
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        title = {
                            val appScreen by simpleNavigator.currentAppScreen.collectAsStateWithLifecycle()
                            val titleRes by remember {
                                derivedStateOf {
                                    when (appScreen) {
                                        is ScreenWelcome -> Res.string.welcome_title
                                        is ScreenInfo -> Res.string.info_title
                                        is ScreenHome -> Res.string.home_title
                                        is ScreenDetail -> Res.string.detail_title
                                        else -> null
                                    }
                                }
                            }
                            titleRes?.let {
                                Text(stringResource(it))
                            }
                        },
                        navigationIcon = {
                            val backStack by navController.currentBackStack.collectAsStateWithLifecycle()
                            val showBackButton by remember {
                                derivedStateOf {
                                    backStack.filterNot { it.destination.route == null }.size > 1
                                }
                            }

                            if (showBackButton) {
                                IconButton(onClick = { simpleNavigator.navigateUp() }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    )
                }
            ) { innerPadding ->
                AppNavHost(
                    simpleNavigator = simpleNavigator,
                    innerPadding = innerPadding,
                    applicationCloseService = applicationCloseService,
                    navController = navController,
                )
            }
        }
    }
}
