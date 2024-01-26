package devendrn.ecb.client.data

import devendrn.ecb.client.database.dao.ProfileDao
import devendrn.ecb.client.database.dao.SubjectDao
import devendrn.ecb.client.database.model.ProfileEntity
import devendrn.ecb.client.database.model.SubjectEntity
import kotlinx.coroutines.flow.Flow

const val USERNAME = "username"
const val PASSWORD = "password"
const val SESSION = "sessionId"

class LocalDataSource(
    private val profileDao: ProfileDao,
    private val subjectDao: SubjectDao
) {

    suspend fun updateProfile(profileEntity: List<ProfileEntity>) = profileDao.updateTable(profileEntity)

    fun readProfile(): Flow<List<ProfileEntity>> = profileDao.readData()

    fun clearProfile() = profileDao.clearTable()

    fun readSubject(): Flow<List<SubjectEntity>> = subjectDao.readAllData()

    suspend fun addSubject(subjectEntity: SubjectEntity) = subjectDao.insertSubject(subjectEntity)

    fun clearSubject() = {}
}