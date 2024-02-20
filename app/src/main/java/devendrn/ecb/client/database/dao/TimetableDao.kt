package devendrn.ecb.client.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import devendrn.ecb.client.database.model.TimetableEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TimetableDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(timetableEntityList: List<TimetableEntity>)

    @Query("SELECT * FROM timetable")
    fun read(): Flow<List<TimetableEntity>>

    @Query("DELETE FROM timetable")
    fun clearTable()
}