package com.voitash.menu.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.voitash.menu.MenuScreen

const val menuRoute = "/menu"

fun NavGraphBuilder.menu(
    onContactSelected: () -> Unit,
) {
    composable(route = menuRoute) {
        MenuScreen(
            onContactSelected = onContactSelected
        )
    }
}

fun NavController.navigateMenu(navOptions: NavOptions.Builder) {
    this.navigate(
        route = menuRoute,
        navOptions = navOptions.build()
    )
}