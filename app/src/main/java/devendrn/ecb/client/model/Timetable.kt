package devendrn.ecb.client.model

data class TimeTable(
    val currentDay: Day,
    val currentMonth: String,
    val currentDate: Int,
    val map: Map<Day, List<String>>
)

enum class Day(val id: Int, val word: String) {
    MON(1,"Monday"),
    TUE(2,"Tuesday"),
    WED(3,"Wednesday"),
    THU(4,"Thursday"),
    FRI(5,"Friday"),
    SAT(6,"Saturday"),
    SUN(7,"Sunday");

    fun firstChar(): Char {
        return word.first()
    }

    companion object {
        fun getEnumFromWord(word: String): Day = when(word) {
            "Monday" -> MON
            "Tuesday" -> TUE
            "Wednesday" -> WED
            "Thursday" -> THU
            "Friday" -> FRI
            "Saturday" -> SAT
            else -> SUN
        }
    }
}
