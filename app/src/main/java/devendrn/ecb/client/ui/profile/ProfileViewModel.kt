package devendrn.ecb.client.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import devendrn.ecb.client.data.EcRepository
import devendrn.ecb.client.data.ProfileDetails
import devendrn.ecb.client.data.UiState
import devendrn.ecb.client.network.NetworkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val networkManager: NetworkManager,
    private val ecRepository: EcRepository
): ViewModel() {
    val profileCardDetails: ProfileDetails = UiState().profileDetails

    val profileDetails: Flow<List<Pair<String, String>>> = ecRepository.getProfileDetails()

    fun signOut() {
        viewModelScope.launch(Dispatchers.IO) {
            networkManager.logout()
        }
    }

    fun updateProfileDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            ecRepository.updateProfileDetails()
        }
    }
}