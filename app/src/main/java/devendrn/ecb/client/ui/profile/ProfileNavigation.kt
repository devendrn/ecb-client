package devendrn.ecb.client.ui.profile

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import devendrn.ecb.client.ui.AppViewModelProvider
import devendrn.ecb.client.ui.profile.screens.EcAbout
import devendrn.ecb.client.ui.profile.screens.EcDetails
import devendrn.ecb.client.ui.profile.screens.EcSettings

const val PROFILE_ROUTE = "profile"

const val PROFILE_START_ROUTE = "profile/start"
const val PROFILE_ABOUT_ROUTE = "profile/about"
const val PROFILE_SETTINGS_ROUTE = "profile/settings"
const val PROFILE_INFO_ROUTE = "profile/info"

fun NavController.navigateToProfile(topLevelNavOptions: NavOptions) = navigate(PROFILE_ROUTE, topLevelNavOptions)

fun NavGraphBuilder.profileScreenRoute(
    onPageClick: (String) -> Unit,
    onSignOut: () -> Unit,
) {
    navigation(
        route = PROFILE_ROUTE,
        startDestination = PROFILE_START_ROUTE
    ) {
        composable(route = PROFILE_START_ROUTE) {
            val profileViewModel: ProfileViewModel = viewModel(factory = AppViewModelProvider.Factory)

            EcProfile(
                profile = profileViewModel.profileCardDetails,
                onItemClick = { destination ->
                    when(destination) {
                        ProfileDestination.INFO -> {
                            profileViewModel.updateProfileDetails()
                            onPageClick(PROFILE_INFO_ROUTE)
                        }
                        ProfileDestination.SETTINGS -> onPageClick(PROFILE_SETTINGS_ROUTE)
                        ProfileDestination.ABOUT -> onPageClick(PROFILE_ABOUT_ROUTE)
                    }
                },
                onSignOutClick = {
                    profileViewModel.signOut()
                    onSignOut()
                },
                onChangePassClick = { },
            )
        }
        composable(route = PROFILE_SETTINGS_ROUTE) {
            EcSettings()
        }
        composable(route = PROFILE_INFO_ROUTE) {
            val profileViewModel: ProfileViewModel = viewModel(factory = AppViewModelProvider.Factory)
            val profileDetailsState = profileViewModel.profileDetails.collectAsState(initial = listOf())

            EcDetails(profileDetailsState.value)
        }
        composable(route = PROFILE_ABOUT_ROUTE) {
            EcAbout()
        }
    }

}