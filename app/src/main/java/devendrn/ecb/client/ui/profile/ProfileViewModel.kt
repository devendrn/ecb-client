package devendrn.ecb.client.ui.profile

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import devendrn.ecb.client.data.EcRepository
import devendrn.ecb.client.data.ProfileDetails
import devendrn.ecb.client.data.UiState
import devendrn.ecb.client.network.NetworkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val networkManager: NetworkManager,
    private val ecRepository: EcRepository
): ViewModel() {
    val profileDetails: ProfileDetails = UiState().profileDetails

    var profileData = mutableStateMapOf<String, String>()
        private set


    fun signOut() {
        viewModelScope.launch(Dispatchers.IO) {
            networkManager.logout()
        }
    }
/*
    init {
        viewModelScope.launch(Dispatchers.IO) {
            launch(Dispatchers.Main) {
                ecRepository.readData().collect {
                    profileData.putAll(
                        it.map { it.data to it.value }.toMap()
                    )
                }
            }
            val profileDetails = Scrapper.getProfileMap()
            saveDetails(profileDetails)
            profileData.putAll(profileDetails)
        }
    }

    private suspend fun saveDetails(details: Map<String, String>) {
        ecRepository.updateTable(
            details.map {
                ProfileEntity(id = 0, data = it.key, value = it.value)
            }
        )
    }

    private suspend fun readData(): Map<String, String> {
        var data = mapOf<String, String>()
        ecRepository.readData().collect {
            data = it.map {
                it.data to it.value
            }.toMap()
        }
        return data
    }

 */
}