package devendrn.ecb.client.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import devendrn.ecb.client.navigation.EcNavHost
import devendrn.ecb.client.navigation.EcTopLevelDestination
import devendrn.ecb.client.network.NetworkManager
import devendrn.ecb.client.ui.auth.LOGIN_ROUTE
import devendrn.ecb.client.ui.components.EcAppBar
import devendrn.ecb.client.ui.components.EcNavBar
import devendrn.ecb.client.ui.home.HOME_ROUTE

@Composable
fun EcApp(
    isLoggedIn: Boolean,
    networkManager: NetworkManager,
    appState: EcAppState = rememberEcAppState(
        networkManager = networkManager
    )
) {
    val navController = appState.navController

    val startDestination: String = if (isLoggedIn) {
        HOME_ROUTE
    } else {
        LOGIN_ROUTE
    }

    val profileDetails = appState.uiState.profileDetails
    val topLevelDestinations = appState.topLevelDestinations

    val currentTopLevelDestination = appState.currentTopLevelDestination
    val currentBaseLevelDestination = appState.currentBaseLevelDestination

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                if (appState.shouldShowTopBar) {
                    val showBackButtonInBar = (currentTopLevelDestination == null)
                    val showProfilePicInBar = (currentBaseLevelDestination != EcTopLevelDestination.PROFILE)
                    val activity by networkManager.activity.collectAsState(initial = null)
                    val isOnline by networkManager.isOnline.collectAsState(initial = true)
                    val lastUpdate by networkManager.lastUpdateTimeString.collectAsState(initial = "")
                    EcAppBar(
                        titleId = appState.currentTitleId,
                        isOnline = isOnline,
                        activityUrl = activity,
                        lastUpdateTime = lastUpdate,
                        showBackButton = showBackButtonInBar,
                        showProfilePic = showProfilePicInBar,
                        navigateBack = { navController.navigateUp() },
                        profilePicUrl = profileDetails.picUrl,
                        onProfileClick = { }
                    )
                }
            },
            bottomBar = {
                if (appState.shouldShowNavBar && currentBaseLevelDestination != null) {
                    EcNavBar(
                        destinations = topLevelDestinations,
                        currentDestination = currentBaseLevelDestination,
                        onNavigateToDestination = appState::navigateToTopLevelDestination
                    )
                }
            }
        ) { padding ->
            EcNavHost(
                appState = appState,
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier.padding(padding)
            )
        }
    }
}
