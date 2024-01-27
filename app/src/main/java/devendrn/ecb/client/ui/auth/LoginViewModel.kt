package devendrn.ecb.client.ui.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import devendrn.ecb.client.network.NetworkManager
import devendrn.ecb.client.network.model.NetworkLoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val networkManager: NetworkManager
): ViewModel() {
    var uiState by mutableStateOf(LoginState())
        private set

    fun updatePassword(password: String) {
        uiState = uiState.copy(password = password.replace(" ", ""))
        uiState = uiState.copy(invalidPassword = false)
    }

    fun updateUsername(username: String) {
        uiState = uiState.copy(username = username)
        uiState = uiState.copy(invalidUsername = false)
    }

    fun submitLogin(
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            uiState = uiState.copy(isChecking = true)
            when(
                networkManager.login(uiState.username, uiState.password)
            ) {
                NetworkLoginResponse.SUCCESS -> {
                    launch(Dispatchers.Main) {
                        onSuccess()
                    }
                }
                NetworkLoginResponse.INVALID_USERNAME -> {
                    uiState = uiState.copy(invalidUsername = true, isChecking = false)
                }
                NetworkLoginResponse.INVALID_PASSWORD -> {
                    uiState = uiState.copy(invalidPassword = true, isChecking = false)
                }
                else -> {
                    uiState = uiState.copy(isChecking = false)
                }
            }
        }
    }
}