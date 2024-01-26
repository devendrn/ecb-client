package devendrn.ecb.client.network.scrapper.model

data class Fraction(
    val value: Int = 0,
    val total: Int = 0
)

data class Subject(
    val name: String,
    val abbr: String,
    val teacher: String,
    val attendance: Fraction,
    val pageId: Int
)
