package devendrn.ecb.client.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import devendrn.ecb.client.data.EcRepository
import devendrn.ecb.client.data.ProfileDetails
import devendrn.ecb.client.network.NetworkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val networkManager: NetworkManager,
    private val ecRepository: EcRepository
): ViewModel() {
    private val _profileCardDetail = MutableStateFlow(
        ProfileDetails(
            "- -", 0, "- -", "- -", 0, ""
        )
    )

    val profileCardDetails: StateFlow<ProfileDetails> = _profileCardDetail
    val profileDetails: Flow<List<Pair<String, String>>> = ecRepository.getProfileDetails()

    init {
        updateProfileCardDetails()
    }

    fun signOut() {
        viewModelScope.launch(Dispatchers.IO) {
            networkManager.logout()
            ecRepository.clearDatabase()
        }
    }

    fun updateProfileCardDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            _profileCardDetail.value = ecRepository.getProfileCardDetails()
        }
    }

    fun updateProfileDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            ecRepository.updateProfileDetails()
        }
    }
}