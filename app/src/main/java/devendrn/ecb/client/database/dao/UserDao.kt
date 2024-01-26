package devendrn.ecb.client.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import devendrn.ecb.client.database.model.UserEntity

@Dao
interface UserDao {
    @Upsert
    fun upsert(userEntityDetail: UserEntity)

    @Query("SELECT value FROM session_details WHERE data = :data")
    fun read(data: String): String

    @Query("DELETE FROM session_details")
    fun clearAll()
}