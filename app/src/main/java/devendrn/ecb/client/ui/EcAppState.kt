package devendrn.ecb.client.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import devendrn.ecb.client.R
import devendrn.ecb.client.data.EcRepository
import devendrn.ecb.client.network.NetworkManager
import devendrn.ecb.client.ui.auth.LOGIN_ROUTE
import devendrn.ecb.client.ui.home.HOME_ASSIGNMENTS_ROUTE
import devendrn.ecb.client.ui.home.HOME_ATTENDANCE_ROUTE
import devendrn.ecb.client.ui.home.HOME_INTERNALS_ROUTE
import devendrn.ecb.client.ui.home.HOME_KTU_RESULTS_ROUTE
import devendrn.ecb.client.ui.home.HOME_ROUTE
import devendrn.ecb.client.ui.home.HOME_SERIES_ROUTE
import devendrn.ecb.client.ui.home.HOME_START_ROUTE
import devendrn.ecb.client.ui.home.HomeDestination
import devendrn.ecb.client.ui.home.navigateToHome
import devendrn.ecb.client.ui.navigation.EcTopLevelDestination
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

@Composable
fun rememberEcAppState(
    networkManager: NetworkManager,
    ecRepository: EcRepository,
    navController: NavHostController = rememberNavController()
): EcAppState {
    return remember(
        networkManager,
        ecRepository,
        navController
    ) {
        EcAppState(
            networkManager,
            ecRepository,
            navController
        )
    }
}

class EcAppState(
    val networkManager: NetworkManager,
    val ecRepository: EcRepository,
    val navController: NavHostController
) {
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
                HOME_KTU_RESULTS_ROUTE -> HomeDestination.KTU_RESULTS.titleId
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

    fun navigateToHomeFromLogin() {
        navController.popBackStack(LOGIN_ROUTE, true)
        navController.navigateToHome(
            navOptions {
                launchSingleTop = true
                restoreState = true
            }
        )
    }
}