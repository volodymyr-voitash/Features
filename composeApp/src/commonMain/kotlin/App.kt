import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.voitash.contact_details.navigation.contactDetails
import com.voitash.contact_details.navigation.navigateToContactDetails
import com.voitash.contact_list.navigation.contactListScreen
import com.voitash.splash.navigation.splashRoute
import com.voitash.splash.navigation.splashScreen
import com.voitash.contact_list.navigation.navigateToContactList
import com.voitash.menu.navigation.menu
import com.voitash.menu.navigation.menuRoute
import com.voitash.menu.navigation.navigateMenu
import di.KoinDI
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    PreComposeApp {
        KoinDI.setupKoin()
        MaterialTheme {
            val navigator = rememberNavigator()
            NavHost(
                navigator = navigator,
                navTransition = NavTransition(),
                initialRoute = splashRoute
            ) {
                splashScreen {
                    navigator.navigateMenu()
                }
                menu(
                    onContactSelected = {
                        navigator.navigateToContactList()
                    }
                )
                contactListScreen { contactId ->
                    navigator.navigateToContactDetails(contactId)
                }
                contactDetails {
                    navigator.goBack()
                }
            }
        }
    }
}
