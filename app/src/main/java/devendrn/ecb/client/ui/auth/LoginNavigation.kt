package devendrn.ecb.client.ui.auth

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import devendrn.ecb.client.ui.AppViewModelProvider

const val LOGIN_ROUTE = "login"

//fun NavController.navigateToLogin(navOptions: NavOptions) = navigate(LOGIN_ROUTE, navOptions)

fun NavGraphBuilder.loginScreenRoute(
    onLoginSuccess: () -> Unit
) {
    composable(route = LOGIN_ROUTE) {
        val loginViewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory)
        val loginUiState = loginViewModel.uiState

        EcLoginScreen(
            username = loginUiState.username,
            password = loginUiState.password,
            isChecking = loginUiState.isChecking,
            invalidUsername = loginUiState.invalidUsername,
            invalidPassword = loginUiState.invalidPassword,
            showErrorDialog = loginUiState.showNetworkErrorDialog,
            onUsernameUpdate = loginViewModel::updateUsername,
            onPasswordUpdate = loginViewModel::updatePassword,
            onSignInClick = {
                loginViewModel.submitLogin {
                    onLoginSuccess()
                }
            },
            onErrorDismiss = {
                loginViewModel.closeNetworkErrorDialog()
            }
        )
    }
}