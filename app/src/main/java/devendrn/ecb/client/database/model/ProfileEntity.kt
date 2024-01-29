package devendrn.ecb.client.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile_details")
data class ProfileEntity(
    //val section: String,
    @PrimaryKey
    val data: String,
    val value: String
)
