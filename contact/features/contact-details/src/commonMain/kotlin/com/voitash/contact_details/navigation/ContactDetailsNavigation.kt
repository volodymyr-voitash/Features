package com.voitash.contact_details.navigation

import com.voitash.contact_details.ContactDetailsScreen
import com.voitash.contact_details.ContactDetailsViewModel
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.transition.NavTransition
import org.koin.compose.getKoin
import org.koin.core.parameter.parametersOf

private const val contactIdArg = "contactId"
private const val contactDetailsRoute = "/contact/{$contactIdArg}"

fun RouteBuilder.contactDetails(
    onNavigateBack: () -> Unit
) {
    scene(route = contactDetailsRoute, navTransition = NavTransition()) { backStackEntry ->
        val id: Int = backStackEntry.path<Int>(contactIdArg)
            ?: throw IllegalArgumentException("Contact id can't be null")
        ContactDetailsScreen(
            viewModel = getKoin().get<ContactDetailsViewModel>(parameters = { parametersOf(id) }),
            onNavigateBack = onNavigateBack
        )
    }
}

fun Navigator.navigateToContactDetails(contactId: Int) {
    this.navigate("/contact/$contactId")
}

