package devendrn.ecb.client.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import devendrn.ecb.client.data.EcRepository
import devendrn.ecb.client.network.NetworkManager
import devendrn.ecb.client.ui.auth.LOGIN_ROUTE
import devendrn.ecb.client.ui.components.EcAppBar
import devendrn.ecb.client.ui.components.EcNavBar
import devendrn.ecb.client.ui.home.HOME_ROUTE
import devendrn.ecb.client.ui.navigation.EcNavHost
import devendrn.ecb.client.ui.navigation.EcTopLevelDestination

@Composable
fun EcApp(
    isLoggedIn: Boolean,
    networkManager: NetworkManager,
    ecRepository: EcRepository,
    appState: EcAppState = rememberEcAppState(
        networkManager = networkManager,
        ecRepository = ecRepository
    )
) {
    val navController = appState.navController
    val ecViewModel = EcViewModel(ecRepository)

    val startDestination: String = if (isLoggedIn) {
        HOME_ROUTE
    } else {
        LOGIN_ROUTE
    }

    val topLevelDestinations = appState.topLevelDestinations

    val currentTopLevelDestination = appState.currentTopLevelDestination
    val currentBaseLevelDestination = appState.currentBaseLevelDestination

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                AnimatedVisibility(
                    visible = appState.shouldShowTopBar,
                    enter = slideInVertically() + expandVertically() + fadeIn()
                ) {
                    val showBackButtonInBar = (currentTopLevelDestination == null)
                    val showProfilePicInBar = (currentBaseLevelDestination != EcTopLevelDestination.PROFILE)
                    val activity by networkManager.activity.collectAsState(initial = null)
                    val isOnline by networkManager.isOnline.collectAsState(initial = false)
                    val lastUpdate by networkManager.lastUpdateTimeString.collectAsState(initial = "")
                    EcAppBar(
                        titleId = appState.currentTitleId,
                        isOnline = isOnline,
                        activityUrl = activity,
                        lastUpdateTime = lastUpdate,
                        showBackButton = showBackButtonInBar,
                        showProfilePic = showProfilePicInBar,
                        navigateBack = { navController.navigateUp() },
                        profilePicUrl = ecViewModel.picUrl.collectAsState("").value,
                        onProfileClick = {
                            appState.navigateToTopLevelDestination(EcTopLevelDestination.PROFILE)
                        }
                    )
                }
            },
            bottomBar = {
                AnimatedVisibility(
                    visible = appState.shouldShowNavBar && currentBaseLevelDestination != null,
                    enter = slideInVertically(
                        initialOffsetY = { it }
                    ),
                    exit = shrinkVertically()
                ) {
                    EcNavBar(
                        destinations = topLevelDestinations,
                        currentDestination = currentBaseLevelDestination?: EcTopLevelDestination.HOME,
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
