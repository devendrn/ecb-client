package devendrn.ecb.client.network.scrapper

import devendrn.ecb.client.network.NetworkManager
import devendrn.ecb.client.network.scrapper.parser.getProfileData

enum class ScrapperStatus {
    IDLE, LOADING, ERROR
}

data class Activity (
    val status: ScrapperStatus = ScrapperStatus.IDLE,
    val message: String = "",
)

class Scrapper(
    private val networkManager: NetworkManager
) {
    var activity = Activity()

    fun getProfile(): Map<String, String> {
        activity = activity.copy(status = ScrapperStatus.LOADING)
        val data = networkManager.getProfileData()
        activity = activity.copy(status = ScrapperStatus.IDLE)
        return data
    }
}

/*
    student/profile
    student/timetable
    ktuacademics/student/results
    ktuacademics/student/
    subjectpool/admin/mainsyllabus/ -> must append id before use
*/