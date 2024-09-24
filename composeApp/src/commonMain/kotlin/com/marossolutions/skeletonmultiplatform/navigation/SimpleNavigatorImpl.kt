package com.marossolutions.skeletonmultiplatform.navigation

import com.marossolutions.skeletonmultiplatform.navigation.NavigationEvent
import com.marossolutions.skeletonmultiplatform.navigation.NavigationOptions
import com.marossolutions.skeletonmultiplatform.navigation.SimpleNavigator
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import com.marossolutions.skeletonmultiplatform.navigation.screens.AppScreen

class SimpleNavigatorImpl : SimpleNavigator {

    private val _navigationEvents = MutableSharedFlow<NavigationEvent>(extraBufferCapacity = 1)
    override val navigationEvents = _navigationEvents.asSharedFlow()

    private val _currentAppScreen = MutableStateFlow<AppScreen?>(null)
    override val currentAppScreen = _currentAppScreen.asStateFlow()

    override fun setCurrentAppScreen(screen: AppScreen?) {
        _currentAppScreen.value = screen
    }

    override fun navigateTo(screen: AppScreen, navigationOptions: NavigationOptions?) {
        _navigationEvents.tryEmit(NavigationEvent.ForwardNavigation(screen, navigationOptions))
    }

    override fun navigateUp() {
        _navigationEvents.tryEmit(NavigationEvent.NavigateUp)
    }
}
