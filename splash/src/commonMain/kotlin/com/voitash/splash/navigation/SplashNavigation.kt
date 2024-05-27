package com.voitash.splash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.voitash.splash.SplashScreen

const val splashRoute = "/splash"

fun NavGraphBuilder.splashScreen(
    doAfterSplash: () -> Unit,
) {
    composable(route = splashRoute) {
        SplashScreen(
            doAfterSplash = doAfterSplash
        )
    }
}