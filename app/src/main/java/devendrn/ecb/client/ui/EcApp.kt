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
import devendrn.ecb.client.network.NetworkStatus
import devendrn.ecb.client.ui.auth.LOGIN_ROUTE
import devendrn.ecb.client.ui.components.EcAppBar
import devendrn.ecb.client.ui.components.EcNavBar
import devendrn.ecb.client.ui.home.HOME_ROUTE

@Composable
fun EcApp(
    networkStatus: NetworkStatus,
    appState: EcAppState = rememberEcAppState(
        networkStatus = networkStatus
    )
) {
    val navController = appState.navController

    val startDestination: String = if (false) {
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
                    val isOnline by networkStatus.isOnline.collectAsState(initial = false)
                    EcAppBar(
                        titleId = appState.currentTitleId,
                        isOnline = isOnline,
                        activityUrl = null,
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
