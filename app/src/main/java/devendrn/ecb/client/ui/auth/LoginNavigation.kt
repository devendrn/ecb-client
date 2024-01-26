package devendrn.ecb.client.ui.auth

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import devendrn.ecb.client.ui.AppViewModelProvider

const val LOGIN_ROUTE = "login"

fun NavController.navigateToLogin(navOptions: NavOptions) = navigate(LOGIN_ROUTE, navOptions)

fun NavGraphBuilder.loginScreenRoute(
    onLoginSuccess: () -> Unit
) {
    composable(route = LOGIN_ROUTE) {

        // TODO - use DI
        val loginViewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory)
        val loginUiState = loginViewModel.uiState

        EcLoginScreen(
            username = loginUiState.username,
            password = loginUiState.password,
            isChecking = loginUiState.isChecking,
            invalidUsername = loginUiState.invalidUsername,
            invalidPassword = loginUiState.invalidPassword,
            onUsernameUpdate = loginViewModel::updateUsername,
            onPasswordUpdate = loginViewModel::updatePassword,
            onForgotPassClick = {
                onLoginSuccess()
            },
            onSignInClick = {
                loginViewModel.submitLogin {
                    onLoginSuccess()
                }
            }
        )
    }
}