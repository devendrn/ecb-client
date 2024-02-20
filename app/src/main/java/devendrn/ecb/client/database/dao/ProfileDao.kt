package devendrn.ecb.client.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import devendrn.ecb.client.database.model.ProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTable(profileEntityList: List<ProfileEntity>)

    @Upsert
    fun upsert(profileEntity: ProfileEntity)

    @Query("DELETE FROM profile_details")
    fun clearTable()

    @Query("SELECT * FROM profile_details WHERE NOT tag = 'hidden'")
    fun readData(): Flow<List<ProfileEntity>>

    @Query("SELECT value from profile_details WHERE data = :data")
    fun getValue(data: String): String?
}