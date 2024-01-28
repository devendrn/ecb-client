package devendrn.ecb.client.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import devendrn.ecb.client.ui.EcAppState
import devendrn.ecb.client.ui.auth.LOGIN_ROUTE
import devendrn.ecb.client.ui.auth.loginScreenRoute
import devendrn.ecb.client.ui.home.homeScreenRoute
import devendrn.ecb.client.ui.news.newsScreenRoute
import devendrn.ecb.client.ui.profile.profileScreenRoute

@Composable
fun EcNavHost(
    appState: EcAppState,
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        loginScreenRoute(
            onLoginSuccess = {
                appState.navigateToTopLevelDestination(EcTopLevelDestination.HOME)
            }
        )
        homeScreenRoute(
            onPageClick = { pageRoute ->
                navController.navigate(pageRoute)
            }
        )
        newsScreenRoute(

        )
        profileScreenRoute(
            onPageClick = { pageRoute ->
                navController.navigate(pageRoute)
            },
            onSignOut = {
                navController.navigate(LOGIN_ROUTE)
            }
        )
    }
}

// TODO - onPageClick() - Fix repeated click resulting in multiple backstack entries