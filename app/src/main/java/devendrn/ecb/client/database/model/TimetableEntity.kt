package devendrn.ecb.client.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timetable")
data class TimetableEntity(
    @PrimaryKey
    val day: String,

    val period1: String,
    val period2: String,
    val period3: String,
    val period4: String,
    val period5: String,
    val period6: String,
)
