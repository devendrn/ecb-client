package devendrn.ecb.client

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import devendrn.ecb.client.ui.EcApp
import devendrn.ecb.client.ui.theme.EcTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        val appContainer = (application as EcApplication).container
        viewModel = MainActivityViewModel(appContainer.ecRepository)

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
        }

        setContent {
            EcTheme(
                darkTheme = isDarkTheme()
            ) {
                EcApp(
                    showLoginScreen = isLoggedIn(uiState)
                )
            }
        }
    }
}

@Composable
private fun isDarkTheme(): Boolean {
    return isSystemInDarkTheme()
}

// TODO - Use a better approach to know if logged in
private fun isLoggedIn(uiState: MainActivityUiState): Boolean {
    return when(uiState) {
        MainActivityUiState.Loading -> false
        is MainActivityUiState.Success -> !uiState.userData.isLoggedIn
    }
}