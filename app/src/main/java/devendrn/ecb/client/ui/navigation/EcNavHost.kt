package devendrn.ecb.client.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import devendrn.ecb.client.ui.AppViewModelProvider
import devendrn.ecb.client.ui.EcAppState
import devendrn.ecb.client.ui.auth.LOGIN_ROUTE
import devendrn.ecb.client.ui.auth.loginScreenRoute
import devendrn.ecb.client.ui.home.HomeViewModel
import devendrn.ecb.client.ui.home.homeScreenRoute
import devendrn.ecb.client.ui.news.NewsViewModel
import devendrn.ecb.client.ui.news.newsScreenRoute
import devendrn.ecb.client.ui.profile.ProfileViewModel
import devendrn.ecb.client.ui.profile.profileScreenRoute

@Composable
fun EcNavHost(
    appState: EcAppState,
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier
) {
    val homeViewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val profileViewModel: ProfileViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val newsViewModel: NewsViewModel = viewModel(factory = AppViewModelProvider.Factory)

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        loginScreenRoute(
            onLoginSuccess = {
                profileViewModel.updateProfileCardDetails()
                appState.navigateToHomeFromLogin()
            }
        )
        homeScreenRoute(
            onPageClick = { pageRoute ->
                navController.navigate(pageRoute)
            },
            homeViewModel = homeViewModel
        )
        newsScreenRoute(
            newsViewModel = newsViewModel
        )
        profileScreenRoute(
            onPageClick = { pageRoute ->
                navController.navigate(pageRoute)
            },
            onSignOut = {
                navController.navigate(LOGIN_ROUTE)
            },
            profileViewModel = profileViewModel
        )
    }
}

// TODO - onPageClick() - Fix repeated click resulting in multiple backstack entries