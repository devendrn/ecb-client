package devendrn.ecb.client.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import devendrn.ecb.client.navigation.EcNavHost
import devendrn.ecb.client.navigation.EcTopLevelDestination
import devendrn.ecb.client.ui.auth.LOGIN_ROUTE
import devendrn.ecb.client.ui.components.EcAppBar
import devendrn.ecb.client.ui.components.EcNavBar
import devendrn.ecb.client.ui.home.HOME_ROUTE

@Composable
fun EcApp(
    appState: EcAppState = rememberEcAppState(),
    showLoginScreen: Boolean
) {
    val navController = appState.navController

    val startDestination: String = if (showLoginScreen) {
        LOGIN_ROUTE
    } else {
        HOME_ROUTE
    }

    val profileDetails = appState.uiState.profileDetails

    val topLevelDestinations = appState.topLevelDestinations

    val currentTopLevelDestination = appState.currentTopLevelDestination
    val currentBaseLevelDestination = appState.currentBaseLevelDestination

    val showBackButtonInBar = (currentTopLevelDestination == null)
    val showProfilePicInBar = (currentBaseLevelDestination != EcTopLevelDestination.PROFILE)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                if (appState.shouldShowTopBar) {
                    EcAppBar(
                        titleId = appState.currentTitleId,
                        activityUrl = null,
                        //activityUrl = rootViewModel.activityUrl.collectAsState().value,
                        showBackButton = showBackButtonInBar,
                        showProfilePic = showProfilePicInBar,
                        navigateBack = { navController.navigateUp() },
                        profilePicUrl = profileDetails.picUrl,
                        onProfileClick = { },
                        onIndicatorClick = { }
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

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: EcTopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false