package devendrn.ecb.client.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import devendrn.ecb.client.data.EcRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EcViewModel(
    ecRepository: EcRepository
): ViewModel() {

    private val _picUrl = MutableStateFlow("")
    val picUrl: StateFlow<String> = _picUrl

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _picUrl.value = ecRepository.getProfilePicUrl()
        }
    }

}
