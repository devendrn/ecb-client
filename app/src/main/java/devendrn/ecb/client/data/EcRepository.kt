package devendrn.ecb.client.data

import devendrn.ecb.client.database.model.SubjectEntity
import devendrn.ecb.client.database.model.TimetableEntity
import devendrn.ecb.client.model.KtuAnnouncement
import devendrn.ecb.client.model.KtuExam
import devendrn.ecb.client.model.KtuExamResult
import devendrn.ecb.client.network.NetworkDataSource
import kotlinx.coroutines.flow.Flow

class EcRepository(
    private val localDataSource: LocalDataSource,
    private val networkDataSource: NetworkDataSource
) {
    fun getProfilePicUrl(): String =
        localDataSource.getProfilePicUrl()
    fun getProfileCardDetails(): ProfileDetails =
        localDataSource.getProfileCard()
    fun getProfileDetails(): Flow<List<Pair<String, String>>> =
        localDataSource.getProfile()

    fun getSubjectAttendance(): Flow<List<Pair<String, Pair<Int, Int>>>> =
        localDataSource.getSubjectAttendance()

    fun getTimetable(): Flow<Map<String, List<String>>> =
        localDataSource.getTimetable()

    fun getKtuProfile(): Map<String, String> =
        localDataSource.getKtuProfile()

    fun getKtuExams(): List<KtuExam> = try {
        networkDataSource.getKtuExams()
    } catch (_: Exception) { listOf() }

    fun getKtuExamResult(
        regNo: String, dob: String, schemeId: Int, examDefId: Int
    ): List<KtuExamResult> = try {
        networkDataSource.getKtuExamResults(regNo, dob, schemeId, examDefId)
    } catch (_: Exception) { listOf() }

    fun getKtuAnnouncements(
        searchText: String = "",
        size: Int = 20,
        pageNo: Int = 0
    ): List<KtuAnnouncement> = try {
        networkDataSource.getKtuAnnouncements(searchText, size, pageNo)
    } catch (_: Exception) { listOf() }

    suspend fun clearDatabase() =
        localDataSource.clearDatabase()


    suspend fun firstStartupUpdate() {
        updateProfileDetails()
        updateTimetable()
    }

    suspend fun updateProfileDetails() = try {
        localDataSource.updateProfile(
            networkDataSource.getProfileDetails()
        )
    } catch(_: Exception) { }

    suspend fun updateSubjectDetails() = try {
        val subjectPageDetails = networkDataSource.getSubjectDetails()
        localDataSource.updateSemesterNo(subjectPageDetails.currentSemester)
        localDataSource.updateSubjects(
            subjectPageDetails.subjects.map {
                SubjectEntity(
                    code = it.key,
                    name = it.value.name,
                    abbr = it.value.abbr,
                    attendance = it.value.attendance,
                    internal = "",
                    series1 = "",
                    series2 = "",
                    series3 = "",
                    series4 = ""
                )
            }
        )
    } catch(_: Exception) { }

    suspend fun updateTimetable() = try {
        val timetable = networkDataSource.getTimetable()
        localDataSource.updateTimetable(
            timetable.map {
                TimetableEntity(
                    day = it.key,
                    period1 = it.value[0],
                    period2 = it.value[1],
                    period3 = it.value[2],
                    period4 = it.value[3],
                    period5 = it.value[4],
                    period6 = it.value[5]
                )
            }
        )
    } catch (_: Exception) { }
}