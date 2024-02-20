package devendrn.ecb.client.ui.news

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import devendrn.ecb.client.data.EcRepository
import devendrn.ecb.client.model.KtuAnnouncement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsViewModel(
    private val ecRepository: EcRepository
): ViewModel() {
    //var ktuAnnouncements by mutableStateOf(listOf<KtuAnnouncement>())
    //    private set

    val ktuAnnouncements = MutableStateFlow<List<KtuAnnouncement>>(listOf())
    var ktuAnnouncementLoading by mutableStateOf(false)
        private set

    init {
        updateKtuAnnouncements()
    }

    fun updateKtuAnnouncements() {
        viewModelScope.launch(Dispatchers.IO) {
            //ktuAnnouncementLoading = true
            ktuAnnouncements.update { ecRepository.getKtuAnnouncements() }
            //ktuAnnouncementLoading = false
        }
    }

}