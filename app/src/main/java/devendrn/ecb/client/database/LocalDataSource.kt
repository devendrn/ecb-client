package devendrn.ecb.client.data

import devendrn.ecb.client.database.dao.ProfileDao
import devendrn.ecb.client.database.dao.SubjectDao
import devendrn.ecb.client.database.dao.TimetableDao
import devendrn.ecb.client.database.model.ProfileEntity
import devendrn.ecb.client.database.model.SubjectEntity
import devendrn.ecb.client.database.model.TimetableEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest

const val USERNAME = "username"
const val PASSWORD = "password"
const val SESSION = "sessionId"

@OptIn(ExperimentalCoroutinesApi::class)
class LocalDataSource(
    private val profileDao: ProfileDao,
    private val subjectDao: SubjectDao,
    private val timetableDao: TimetableDao
) {

    fun getProfilePicUrl(): String =
        profileDao.getValue("photoUrl")?: ""

    fun getProfileCard(): ProfileDetails {
        return ProfileDetails(
            name = profileDao.getValue("Name")?: " - ",
            picUrl = getProfilePicUrl(),
            semesterNo = profileDao.getValue("semester")?.toInt()?: 0,
            branch = profileDao.getValue("batch")?: " - ",
            roll = profileDao.getValue("Admission No")?: " - ",
            admissionNo = (profileDao.getValue("Admission No")?: "0").toInt()
        )
    }

    fun getProfile(): Flow<List<Pair<String, String>>> {
        return profileDao.readData().mapLatest {
            it.filter { item ->
                item.tag != "Hidden"
            }.map { item ->
                item.data to item.value
            }
        }
    }

    fun getSubjectAttendance(): Flow<List<Pair<String, Pair<Int, Int>>>> {
        return subjectDao.getAttendance().mapLatest {
            println("$it")
            it.map { entry ->
                val value = entry.value.substringBefore("/").toInt()
                val total = entry.value.substringAfter("/").toInt()
                entry.name to (value to total)
            }
        }
    }

    fun getTimetable(): Flow<Map<String, List<String>>> =
        timetableDao.read().map {
            it.associate { entry ->
                entry.day to listOf(
                    entry.period1,
                    entry.period2,
                    entry.period3,
                    entry.period4,
                    entry.period5,
                    entry.period6
                ).map { subjects ->
                    subjects
                        .split(" ")
                        .filter { word -> word !in listOf("AND", "&") }
                        .map { word -> if(word.isNotEmpty()) word.substring(0,1) else "-"}
                        .joinToString("")
                }
            }
        }

    fun getKtuProfile(): Map<String, String> = mapOf(
        "Name" to (profileDao.getValue("Name")?:""),
        "Date of birth " to (profileDao.getValue("Date of Birth")?:""),
        "Reg No" to (profileDao.getValue("University Reg No")?:""),
    )

    suspend fun updateProfile(profileEntity: List<ProfileEntity>) =
        profileDao.updateTable(profileEntity)

    fun updateSemesterNo(semester: Int) {
        profileDao.upsert(
            ProfileEntity("hidden", "semester", semester.toString())
        )
    }

    suspend fun updateSubjects(subjects: List<SubjectEntity>) {
        subjectDao.updateSubjects(subjects)
    }

    suspend fun updateTimetable(timetable: List<TimetableEntity>) {
        timetableDao.upsert(timetable)
    }

    fun clearDatabase() {
        profileDao.clearTable()
        subjectDao.clearTable()
        timetableDao.clearTable()
    }
}