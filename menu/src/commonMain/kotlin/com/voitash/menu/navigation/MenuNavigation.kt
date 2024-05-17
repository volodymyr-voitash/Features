package com.voitash.menu.navigation

import com.voitash.menu.MenuScreen
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.PopUpTo
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

fun Navigator.navigateMenu() {
    this.navigate(
        route = menuRoute,
        options = NavOptions(
            popUpTo = PopUpTo.First()
        )
    )
}