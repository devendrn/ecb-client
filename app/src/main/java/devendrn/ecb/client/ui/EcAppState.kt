package devendrn.ecb.client.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import devendrn.ecb.client.R
import devendrn.ecb.client.data.UiState
import devendrn.ecb.client.navigation.EcTopLevelDestination
import devendrn.ecb.client.network.NetworkStatus
import devendrn.ecb.client.ui.home.HOME_ASSIGNMENTS_ROUTE
import devendrn.ecb.client.ui.home.HOME_ATTENDANCE_ROUTE
import devendrn.ecb.client.ui.home.HOME_INTERNALS_ROUTE
import devendrn.ecb.client.ui.home.HOME_ROUTE
import devendrn.ecb.client.ui.home.HOME_SERIES_ROUTE
import devendrn.ecb.client.ui.home.HOME_START_ROUTE
import devendrn.ecb.client.ui.home.HomeDestination
import devendrn.ecb.client.ui.home.navigateToHome
import devendrn.ecb.client.ui.news.NEWS_ROUTE
import devendrn.ecb.client.ui.news.NEWS_START_ROUTE
import devendrn.ecb.client.ui.news.navigateToNews
import devendrn.ecb.client.ui.profile.PROFILE_ABOUT_ROUTE
import devendrn.ecb.client.ui.profile.PROFILE_INFO_ROUTE
import devendrn.ecb.client.ui.profile.PROFILE_ROUTE
import devendrn.ecb.client.ui.profile.PROFILE_SETTINGS_ROUTE
import devendrn.ecb.client.ui.profile.PROFILE_START_ROUTE
import devendrn.ecb.client.ui.profile.ProfileDestination
import devendrn.ecb.client.ui.profile.navigateToProfile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberEcAppState(
    networkStatus: NetworkStatus,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController()
): EcAppState {
    return remember(
        networkStatus,
        coroutineScope,
        navController
    ) {
        EcAppState(
            networkStatus,
            coroutineScope,
            navController
        )
    }
}

class EcAppState(
    val networkStatus: NetworkStatus,
    val coroutineScope: CoroutineScope,
    val navController: NavHostController
) {
    // TODO - Remove this
    val uiState: UiState = UiState()

    val isLoggedIn = networkStatus.isLoggedIn.
        map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false,
        )

    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: EcTopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            HOME_START_ROUTE -> EcTopLevelDestination.HOME
            NEWS_START_ROUTE -> EcTopLevelDestination.NEWS
            PROFILE_START_ROUTE -> EcTopLevelDestination.PROFILE
            else -> null
        }

    val currentBaseLevelDestination: EcTopLevelDestination?
        @Composable get() = when (currentDestination?.route?.substringBefore('/')) {
            HOME_ROUTE -> EcTopLevelDestination.HOME
            NEWS_ROUTE -> EcTopLevelDestination.NEWS
            PROFILE_ROUTE -> EcTopLevelDestination.PROFILE
            else -> null
        }

    val topLevelDestinations: List<EcTopLevelDestination> = EcTopLevelDestination.entries

    val shouldShowTopBar: Boolean
        @Composable
        get() = currentBaseLevelDestination != null

    val shouldShowNavBar: Boolean
        @Composable
        get() = currentTopLevelDestination != null

    // TODO - Simplify this
    val currentTitleId: Int
        @Composable
        get() = if (shouldShowNavBar) {
            R.string.app_name
        } else {
            when (currentDestination?.route) {
                HOME_ASSIGNMENTS_ROUTE -> HomeDestination.ASSIGNMENTS.titleId
                HOME_SERIES_ROUTE -> HomeDestination.SERIES.titleId
                HOME_ATTENDANCE_ROUTE -> HomeDestination.ATTENDANCE.titleId
                HOME_INTERNALS_ROUTE -> HomeDestination.INTERNALS.titleId
                PROFILE_ABOUT_ROUTE -> ProfileDestination.ABOUT.titleId
                PROFILE_SETTINGS_ROUTE -> ProfileDestination.SETTINGS.titleId
                PROFILE_INFO_ROUTE -> ProfileDestination.INFO.titleId
                else -> R.string.app_name
            }
        }

    fun navigateToTopLevelDestination(destination: EcTopLevelDestination) {
        val topLevelNavOptions = navOptions {
            launchSingleTop = true
            restoreState = true
        }

        navController.popBackStack(HOME_START_ROUTE, false)

        when (destination) {
            EcTopLevelDestination.HOME -> navController.navigateToHome(topLevelNavOptions)
            EcTopLevelDestination.NEWS -> navController.navigateToNews(topLevelNavOptions)
            EcTopLevelDestination.PROFILE -> navController.navigateToProfile(topLevelNavOptions)
        }
    }

}