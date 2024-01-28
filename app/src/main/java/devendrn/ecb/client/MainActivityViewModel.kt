package devendrn.ecb.client

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import devendrn.ecb.client.network.NetworkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val networkManager: NetworkManager
): ViewModel() {

    private val _hasLoaded = MutableStateFlow(false)
    private val _isLoggedIn = MutableStateFlow(false)

    val hasLoaded: StateFlow<Boolean> = _hasLoaded.asStateFlow()
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            networkManager.retrieveSession()
            _hasLoaded.value = true
            _isLoggedIn.value = networkManager.isLoggedIn()
        }
    }
}