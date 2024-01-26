package devendrn.ecb.client.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import devendrn.ecb.client.database.model.ProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateTable(profileEntityList: List<ProfileEntity>)

    @Query("DELETE FROM profile_details")
    fun clearTable()

    @Query("SELECT * FROM profile_details")
    fun readData(): Flow<List<ProfileEntity>>

}