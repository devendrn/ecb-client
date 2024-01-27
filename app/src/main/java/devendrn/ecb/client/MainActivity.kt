package devendrn.ecb.client

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import devendrn.ecb.client.ui.EcApp
import devendrn.ecb.client.ui.theme.EcTheme

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        val appContainer = (application as EcApplication).container

        /*
        viewModel = MainActivityViewModel(appContainer.ecRepository, appContainer.networkManager)

        var uiState: MainActivityUiState by mutableStateOf(MainActivityUiState.Loading)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    uiState = it
                }
            }
        }

        splashScreen.setKeepOnScreenCondition {
            uiState == MainActivityUiState.Loading
        }*/

        setContent {
            EcTheme(
                darkTheme = isDarkTheme()
            ) {
                EcApp(appContainer.networkStatus)
            }
        }
    }
}

@Composable
private fun isDarkTheme(): Boolean {
    return isSystemInDarkTheme()
}