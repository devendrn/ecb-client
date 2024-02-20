package devendrn.ecb.client.network.scrapper.parser

import devendrn.ecb.client.database.model.ProfileEntity
import devendrn.ecb.client.network.NetworkManager
import devendrn.ecb.client.network.NetworkUrl
import org.jsoup.nodes.Document

const val PROFILE_URL = "${NetworkUrl.HOME}student/profile"

fun NetworkManager.getProfileData(): List<ProfileEntity> {
    return getProfileMap(getPage(PROFILE_URL))
}

const val UNIVERSITY_REG_LABEL = "University Reg No"

private fun getProfileMap(doc: Document): List<ProfileEntity> {
    val batch = doc.select("center span a").text()
    val photo = doc.select("img#photo").attr("src")
    val photoUrl = if (photo.isNotEmpty()) NetworkUrl.HOME + photo else ""

    val personalDetails = doc.select(
        ":is(:contains(personal details), :contains(parent details)) + div tbody tr"
    ).map {
        it.select("th").text() to it.select("td").text().capitalizeText()
    }.filter { it.second.isNotEmpty() }.toMap().toMutableMap()

    personalDetails[UNIVERSITY_REG_LABEL]?.let {
        personalDetails [UNIVERSITY_REG_LABEL] = it.uppercase()
    }

    // TODO - do email decryption to fix "[email protected]" value

    return listOf(
        ProfileEntity("Hidden", "batch", batch),
        ProfileEntity("Hidden", "photoUrl", photoUrl)
    ) + personalDetails.map { entry ->
        ProfileEntity("Personal", entry.key, entry.value)
    }
}

private fun String.capitalizeText(): String {
    return this
        .split(' ')
        .joinToString(" ") { it.lowercase().replaceFirstChar(Char::uppercase) }
}