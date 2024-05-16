import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.voitash.contact_details.navigation.contactDetails
import com.voitash.contact_details.navigation.navigateToContactDetails
import com.voitash.contact_list.navigation.contactListRoute
import com.voitash.contact_list.navigation.contactListScreen
import di.KoinDI
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.core.context.KoinContext

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
                initialRoute = contactListRoute
            ) {
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
