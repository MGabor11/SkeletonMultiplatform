package com.marossolutions.skeletonmultiplatform.ui.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marossolutions.skeletonmultiplatform.datastore.AppInfoPreferences
import kotlinx.coroutines.launch
import com.marossolutions.skeletonmultiplatform.navigation.NavigationOptions
import com.marossolutions.skeletonmultiplatform.navigation.SimpleNavigator
import com.marossolutions.skeletonmultiplatform.navigation.screens.ScreenHome
import com.marossolutions.skeletonmultiplatform.navigation.screens.ScreenWelcome

class InfoViewModel(
    private val appInfoPreferences: AppInfoPreferences,
    private val navigator: SimpleNavigator,
) : ViewModel() {

    fun navigateToHome() {
        viewModelScope.launch {
            appInfoPreferences.setInfoViewed(true)
            navigator.navigateTo(
                screen = ScreenHome,
                navigationOptions = NavigationOptions(
                    popUpToScreen = ScreenWelcome,
                    popUpToInclusive = true,
                )
            )
        }
    }
}
