package com.voitash.contact_details.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.voitash.contact_details.ContactDetailsScreen
import com.voitash.contact_details.ContactDetailsViewModel
import org.koin.core.parameter.parametersOf
import org.koin.mp.KoinPlatform.getKoin

private const val contactIdArg = "contactId"
private const val contactDetailsRoute = "/contact/{$contactIdArg}"

fun NavGraphBuilder.contactDetails(onNavigateBack: () -> Unit) {
    composable(
        route = contactDetailsRoute,
        arguments = listOf(navArgument(contactIdArg) { type = NavType.IntType })
    ) {
        ContactDetailsScreen(
            viewModel = viewModel {
                getKoin().get<ContactDetailsViewModel>(
                    parameters = { parametersOf(it.arguments?.getInt(contactIdArg)) }
                )
            },
            onNavigateBack = onNavigateBack
        )
    }
}

fun NavController.navigateToContactDetails(contactId: Int) {
    this.navigate("/contact/$contactId")
}