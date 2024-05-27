import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import di.KoinDI
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
        KoinDI.setupKoin()
        MaterialTheme {
            MainNavigation()
        }
}

