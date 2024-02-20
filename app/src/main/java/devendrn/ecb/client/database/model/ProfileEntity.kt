package devendrn.ecb.client.database.model

import androidx.room.Entity

@Entity(tableName = "profile_details", primaryKeys = ["tag", "data"])
data class ProfileEntity(
    val tag: String,
    val data: String,
    val value: String
)
