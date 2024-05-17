package com.voitash.menu.navigation

import com.voitash.menu.MenuScreen
import moe.tlaster.precompose.navigation.RouteBuilder
import moe.tlaster.precompose.navigation.transition.NavTransition

const val menuRoute = "/menu"

fun RouteBuilder.menu(
    onContactSelected: () -> Unit,
) {
    scene(route = menuRoute, navTransition = NavTransition()) {
        MenuScreen(
            onContactSelected = onContactSelected
        )
    }
}