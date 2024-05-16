package com.voitash.contact_list.navigation

import com.voitash.contact_list.ContactListScreen
import com.voitash.contact_list.ContactListViewModel
import moe.tlaster.precompose.navigation.RouteBuilder
import moe.tlaster.precompose.navigation.transition.NavTransition
import org.koin.compose.koinInject

const val contactListRoute = "/contactList"

fun RouteBuilder.contactListScreen(
    onNavigateToContactDetails: (Int) -> Unit,
) {
    scene(route = contactListRoute, navTransition = NavTransition()) {
        ContactListScreen(
            viewModel = koinInject(),
            onNavigateToContactDetails = onNavigateToContactDetails
        )
    }
}