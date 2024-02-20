package devendrn.ecb.client.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import devendrn.ecb.client.EcApplication
import devendrn.ecb.client.ui.auth.LoginViewModel
import devendrn.ecb.client.ui.home.HomeViewModel
import devendrn.ecb.client.ui.news.NewsViewModel
import devendrn.ecb.client.ui.profile.ProfileViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            ProfileViewModel(
                ecApplication().container.networkManager,
                ecApplication().container.ecRepository
            )
        }
        initializer {
            LoginViewModel(
                ecApplication().container.ecRepository,
                ecApplication().container.networkManager
            )
        }
        initializer {
            HomeViewModel(
                ecApplication().container.ecRepository
            )
        }
        initializer {
            NewsViewModel(
                ecApplication().container.ecRepository
            )
        }
    }
}

fun CreationExtras.ecApplication(): EcApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as EcApplication)