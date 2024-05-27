package com.voitash.contact_list.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.voitash.contact_list.ContactListScreen
import com.voitash.contact_list.ContactListViewModel
import org.koin.mp.KoinPlatform.getKoin

const val contactListRoute = "/contactList"

fun NavGraphBuilder.contactListScreen(
    onNavigateToContactDetails: (Int) -> Unit,
) {
    composable(route = contactListRoute) {
        ContactListScreen(
            viewModel = viewModel { getKoin().get<ContactListViewModel>() },
            onNavigateToContactDetails = onNavigateToContactDetails
        )
    }
}

fun NavController.navigateToContactList() {
    this.navigate(contactListRoute)
}