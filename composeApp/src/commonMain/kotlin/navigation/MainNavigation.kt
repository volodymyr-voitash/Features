import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.voitash.contact_details.navigation.contactDetails
import com.voitash.contact_details.navigation.navigateToContactDetails
import com.voitash.contact_list.navigation.contactListScreen
import com.voitash.contact_list.navigation.navigateToContactList
import com.voitash.menu.navigation.menu
import com.voitash.menu.navigation.navigateMenu
import com.voitash.splash.navigation.splashRoute
import com.voitash.splash.navigation.splashScreen

@Composable
fun MainNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = splashRoute) {
        splashScreen {
            navController.navigateMenu(NavOptions.Builder().setPopUpTo(splashRoute, true))
        }
        menu(
            onContactSelected = {
                navController.navigateToContactList()
            }
        )
        contactListScreen { contactId ->
            navController.navigateToContactDetails(contactId)
        }
        contactDetails {
            navController.navigateUp()
        }
    }
}