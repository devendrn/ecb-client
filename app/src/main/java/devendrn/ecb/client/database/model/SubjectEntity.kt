package devendrn.ecb.client.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subjects")
data class SubjectEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val name: String,
    val code: String,
    val abbr: String,

    val attendanceValue: Int,
    val attendanceTotal: Int,

    val series1Value: Int,
    val series1Total: Int,
    val series2Value: Int,
    val series2Total: Int,

    val internalValue: Int,
    val internalTotal: Int
)
