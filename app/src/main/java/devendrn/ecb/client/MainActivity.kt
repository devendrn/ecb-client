package devendrn.ecb.client

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import devendrn.ecb.client.ui.EcApp
import devendrn.ecb.client.ui.theme.EcTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        val appContainer = (application as EcApplication).container

        val viewModel = MainActivityViewModel(appContainer.networkManager)

        splashScreen.setKeepOnScreenCondition {
            !viewModel.hasLoaded.value
        }

        setContent {
            EcTheme(
                darkTheme = isDarkTheme()
            ) {
                EcApp(
                    isLoggedIn = viewModel.isLoggedIn.collectAsState().value,
                    networkManager = appContainer.networkManager
                )
            }
        }
    }
}

@Composable
private fun isDarkTheme(): Boolean {
    return isSystemInDarkTheme()
}