package devendrn.ecb.client.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import devendrn.ecb.client.EcApplication
import devendrn.ecb.client.ui.auth.LoginViewModel
import devendrn.ecb.client.ui.profile.ProfileViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            ProfileViewModel(
                ecApplication().container.ecRepository
            )
        }
        initializer {
            LoginViewModel(
                ecApplication().container.networkManager
            )
        }
    }
}

fun CreationExtras.ecApplication(): EcApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as EcApplication)