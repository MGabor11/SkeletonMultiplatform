package com.marossolutions.skeletonmultiplatform.ui.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marossolutions.skeletonmultiplatform.navigation.SimpleNavigator
import com.marossolutions.skeletonmultiplatform.datastore.AppInfoPreferences
import kotlinx.coroutines.launch
import com.marossolutions.skeletonmultiplatform.navigation.NavigationOptions
import com.marossolutions.skeletonmultiplatform.navigation.screens.ScreenHome
import com.marossolutions.skeletonmultiplatform.navigation.screens.ScreenInfo
import com.marossolutions.skeletonmultiplatform.navigation.screens.ScreenWelcome

class WelcomeViewModel(
    private val appInfoPreferences: AppInfoPreferences,
    private val navigator: SimpleNavigator,
) : ViewModel() {

    fun navigateToNextScreen() {
        viewModelScope.launch {
            val isInfoViewed = appInfoPreferences.isInfoViewed()
            navigator.navigateTo(
                screen = if (isInfoViewed) {
                    ScreenHome
                } else {
                    ScreenInfo
                },
                navigationOptions = if (isInfoViewed) {
                    NavigationOptions(
                        popUpToScreen = ScreenWelcome,
                        popUpToInclusive = true
                    )
                } else {
                    null
                }
            )
        }
    }
}