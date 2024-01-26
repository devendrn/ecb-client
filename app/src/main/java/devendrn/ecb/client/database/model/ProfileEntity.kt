package devendrn.ecb.client.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile_details")
data class ProfileEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val data: String,
    val value: String
)
