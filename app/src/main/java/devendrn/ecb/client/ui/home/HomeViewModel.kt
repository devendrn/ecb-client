package devendrn.ecb.client.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import devendrn.ecb.client.data.EcRepository
import devendrn.ecb.client.data.LabeledFraction
import devendrn.ecb.client.data.UiState
import devendrn.ecb.client.model.Day
import devendrn.ecb.client.model.KtuExam
import devendrn.ecb.client.model.KtuExamResult
import devendrn.ecb.client.model.TimeTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar

class HomeViewModel(
    private val ecRepository: EcRepository
): ViewModel() {

    val uiState = UiState()

    private val _attendanceEntries: Flow<List<Pair<String, Pair<Int, Int>>>> = ecRepository.getSubjectAttendance()
    private val _timetableEntries: Flow<Map<String, List<String>>> = ecRepository.getTimetable()
    var ktuProfile by mutableStateOf(mapOf<String, String>())
        private set
    var ktuExams by mutableStateOf(listOf<KtuExam>())
        private set
    var ktuExamResults by mutableStateOf(listOf<KtuExamResult>())
        private set
    var ktuResultLoading by mutableStateOf(false)
        private set

    var ktuExamSelected by mutableStateOf("Select an exam")
        private set

    val attendanceEntries = _attendanceEntries.map {
        it.map { entry ->
            LabeledFraction(
                label = entry.first,
                value = entry.second.first,
                total = entry.second.second
            )
        }
    }

    val timetableEntries = _timetableEntries.map {
        val time = Calendar.getInstance().time
        TimeTable(
            currentDate = SimpleDateFormat("dd").format(time).toInt(),
            currentDay = Day.getEnumFromWord(SimpleDateFormat("EEEE").format(time)),
            currentMonth = SimpleDateFormat("MMMM").format(time),
            map = it.map { row ->
                val day = Day.getEnumFromWord(row.key)
                day to row.value
            }.toMap()
        )
    }

    init {
        updateTimetable()
    }

    fun updateSubjectEntries() {
        viewModelScope.launch(Dispatchers.IO) {
            ecRepository.updateSubjectDetails()
        }
    }

    fun updateKtuExamEntries() {
        viewModelScope.launch(Dispatchers.IO) {
            ktuProfile = ecRepository.getKtuProfile()
            ktuExams = ecRepository.getKtuExams()
        }
    }

    fun updateKtuExamResultEntries(name: String, schemeId: Int, examDefId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            ktuResultLoading = true
            ktuExamSelected = name
            ktuExamResults = ecRepository.getKtuExamResult(
                regNo = ktuProfile["Reg No"]?:"",
                dob = ktuProfile["Date of birth"]?:"",
                schemeId = schemeId,
                examDefId = examDefId
            )
            ktuResultLoading = false
        }
    }

    fun updateTimetable() {
        viewModelScope.launch(Dispatchers.IO) {
            ecRepository.updateTimetable()
        }
    }

}