package devendrn.ecb.client.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "session_details")
data class UserEntity(
    @PrimaryKey
    val data: String,
    val value: String
)