package devendrn.ecb.client

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import devendrn.ecb.client.data.EcRepository
import devendrn.ecb.client.model.UserData
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainActivityViewModel (
    ecRepository: EcRepository,
): ViewModel() {
    val uiState: StateFlow<MainActivityUiState> = ecRepository.userData.map {
        MainActivityUiState.Success(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = MainActivityUiState.Loading,
        started = SharingStarted.WhileSubscribed(5000),
    )
}

sealed interface MainActivityUiState {
    data object Loading: MainActivityUiState
    data class Success(val userData: UserData): MainActivityUiState
}
