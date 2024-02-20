package devendrn.ecb.client.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import devendrn.ecb.client.database.model.SubjectAttendance
import devendrn.ecb.client.database.model.SubjectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubject(subjectEntity: SubjectEntity)

    @Update
    suspend fun updateSubject(subjectEntity: SubjectEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSubjects(subjectEntityList: List<SubjectEntity>)

    @Query("DELETE FROM subjects")
    fun clearTable()

    @Query("SELECT * from subjects WHERE code = :code")
    fun getSubjectByCode(code: String): Flow<SubjectEntity>

    @Query("SELECT * FROM subjects")
    fun readAllData(): Flow<List<SubjectEntity>>

    @Query("SELECT name, attendance FROM subjects")
    fun getAttendance(): Flow<List<SubjectAttendance>>
}