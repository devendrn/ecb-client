package devendrn.ecb.client.misc

data class Contributor(
    val name: String,
    val role: String,
    val link: String
)

val contributors = listOf(
    Contributor("Devendran S S", "Developer", "https://github.com/devendrn")
)