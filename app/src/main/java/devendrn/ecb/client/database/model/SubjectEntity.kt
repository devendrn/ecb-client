package devendrn.ecb.client.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subjects")
data class SubjectEntity(
    @PrimaryKey
    val code: String,
    val name: String,
    val abbr: String,

    val attendance: String,
    val internal: String,

    val series1: String,
    val series2: String,
    val series3: String,
    val series4: String,
)

data class SubjectAttendance(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "attendance") val value: String
)

