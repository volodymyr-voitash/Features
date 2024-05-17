package com.voitash.splash.navigation

import com.voitash.splash.SplashScreen
import moe.tlaster.precompose.navigation.RouteBuilder
import moe.tlaster.precompose.navigation.transition.NavTransition

const val splashRoute = "/splash"

fun RouteBuilder.splashScreen(
    doAfterSplash: () -> Unit,
) {
    scene(route = splashRoute, navTransition = NavTransition()) {
        SplashScreen(
            doAfterSplash = doAfterSplash
        )
    }
}