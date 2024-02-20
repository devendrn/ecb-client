package devendrn.ecb.client.network

import devendrn.ecb.client.database.model.ProfileEntity
import devendrn.ecb.client.model.KtuAnnouncement
import devendrn.ecb.client.model.KtuExam
import devendrn.ecb.client.model.KtuExamResult
import devendrn.ecb.client.network.scrapper.model.SubjectPageDetails
import devendrn.ecb.client.network.scrapper.parser.getProfileData
import devendrn.ecb.client.network.scrapper.parser.getSubjectsData
import devendrn.ecb.client.network.scrapper.parser.getTimetable

class NetworkDataSource(
    private val networkManager: NetworkManager
) {
    fun getProfileDetails(): List<ProfileEntity> =
        networkManager.getProfileData()

    fun getSubjectDetails(): SubjectPageDetails =
        networkManager.getSubjectsData()

    fun getTimetable(): Map<String, List<String>> =
        networkManager.getTimetable()

    fun getKtuExams(): List<KtuExam> =
        networkManager.ktuApi.getExams()

    fun getKtuExamResults(
        regNo: String, dob: String, schemeId: Int, examDefId: Int
    ): List<KtuExamResult> =
        networkManager.ktuApi.getExamResults(regNo, dob, examDefId, schemeId)

    fun getKtuAnnouncements(
        searchText: String, size: Int, pageNo: Int,
    ): List<KtuAnnouncement> =
        networkManager.ktuApi.getAnnouncements(searchText, size, pageNo)

}