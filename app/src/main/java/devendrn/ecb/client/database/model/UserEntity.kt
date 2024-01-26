package devendrn.ecb.client.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "session_details")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val data: String,
    val value: String
)