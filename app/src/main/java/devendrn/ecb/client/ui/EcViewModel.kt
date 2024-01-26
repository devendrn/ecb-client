package devendrn.ecb.client.ui

import androidx.lifecycle.ViewModel
import devendrn.ecb.client.data.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EcViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun toggleExpandTimetable() {
        _uiState.update { currentState ->
            currentState.copy(isTimetableExpanded = !currentState.isTimetableExpanded)
        }
    }

    fun changeNavIndex(toIndex: Int) {
        _uiState.update { currentState ->
            currentState.copy(navIndex = toIndex)
        }
    }

    fun changeSeriesDefaultTab(toNo: Int) {
        _uiState.update {
            it.copy(defaultSeriesSelection = toNo)
        }
    }
}
