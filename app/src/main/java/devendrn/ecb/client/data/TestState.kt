package devendrn.ecb.client.data

// dirty test code - should be removed!

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
}

data class TimeTable(
    val currentDay: Day,
    val map: Map<Day, List<String>>
)

data class LabeledFraction(
    val label: String,
    val value: Int,
    val total: Int
)

data class ProfileDetails(
    val name: String,
    val semesterNo: Int,
    val branch: String,
    val roll: String,
    val admissionNo: Int,
    val picUrl: String
)

data class UiState(
    val username: String = "",
    val profileDetails: ProfileDetails = ProfileDetails(
        name = "Maxim Nausea",
        semesterNo = 5,
        branch = "ECE",
        roll = "B21EC123",
        admissionNo = 210000,
        picUrl = "hsttps://photos.costume-works.com/thumbs/monster_cat.jpg"
    ),
    val personalDetails: List<Pair<String, String>> = listOf(
        "Name" to "Maxim Nausea",
        "Gender" to "Cat (Male)",
        "Date of Birth" to "12/04/2019",
        "Department" to "Department of Cat Engineering 2021",
        "Admission No" to "21000",
        "University Reg No" to "TKM21CAT15"
    ),
    val timeTable: TimeTable = TimeTable(
        currentDay = Day.WED,
        mapOf(
            Day.MON to listOf("DSP", "DSP", "LIC", "MOE", "ADC", "CS"),
            Day.TUE to listOf("ADC", "ADC", "DM", "ADC", "MOE", "DSP"),
            Day.WED to listOf("MOE", "LIC", "ADC", "CS", "DSP", "DM"),
            Day.THU to listOf("CS", "ADC", "LIC", "LIC", "MOE", "DSP"),
            Day.FRI to listOf("DM", "ADC", "MOE", "MOE", "ADC", "ADC")
        )
    ),
    val isTimetableExpanded: Boolean = false,
    val navIndex: Int = 0,
    val defaultSeriesSelection: Int = 1,
    val attendanceEntries: List<LabeledFraction> = listOf(
        LabeledFraction("Control Systems", 43, 46),
        LabeledFraction("Digital Signal Processing", 33, 36),
        LabeledFraction("Linear Integrated Circuits", 38, 41),
        LabeledFraction("Analog and Digital Communication", 36, 39),
        LabeledFraction("Disaster Management", 19, 19),
        LabeledFraction("Management for Engineers", 20, 24),
        LabeledFraction("Digital Signal Processing Lab", 24, 24),
        LabeledFraction("Analog Integrated Circuits and Simulation Lab", 30, 30)
    ),
    val series1Entries: List<LabeledFraction> = listOf(
        LabeledFraction("Control Systems", 43, 50),
        LabeledFraction("Digital Signal Processing", 33, 50),
        LabeledFraction("Linear Integrated Circuits", 38, 50),
        LabeledFraction("Analog and Digital Communication", 36, 50),
        LabeledFraction("Disaster Management", 19, 50),
        LabeledFraction("Management for Engineers", 20, 50),
    ),
    val series2Entries: List<LabeledFraction> = listOf(),
    val assignmentsEntries: List<LabeledFraction> = listOf(
        LabeledFraction("Control Systems", 43, 46),
        LabeledFraction("Digital Signal Processing", 33, 36),
        LabeledFraction("Linear Integrated Circuits", 38, 41),
        LabeledFraction("Analog and Digital Communication", 36, 39),
        LabeledFraction("Disaster Management", 19, 19),
        LabeledFraction("Management for Engineers", 20, 24),
        LabeledFraction("Digital Signal Processing Lab", 24, 24),
        LabeledFraction("Analog Integrated Circuits and Simulation Lab", 30, 30)
    ),
    val internalsEntries: List<LabeledFraction> = listOf(
        LabeledFraction("Control Systems", 43, 46),
        LabeledFraction("Digital Signal Processing", 33, 36),
        LabeledFraction("Linear Integrated Circuits", 38, 41),
        LabeledFraction("Analog and Digital Communication", 36, 39),
        LabeledFraction("Disaster Management", 19, 19),
        LabeledFraction("Management for Engineers", 20, 24),
        LabeledFraction("Digital Signal Processing Lab", 24, 24),
        LabeledFraction("Analog Integrated Circuits and Simulation Lab", 30, 30)
    )

)