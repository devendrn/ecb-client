package devendrn.ecb.client.network.scrapper.parser

import devendrn.ecb.client.network.NetworkManager
import devendrn.ecb.client.network.NetworkUrl
import org.jsoup.nodes.Document

const val PROFILE_URL = "${NetworkUrl.HOME}student/profile"

fun NetworkManager.getProfileData(): Map<String, String> {
    return getProfileMap(getPage(PROFILE_URL))
}

const val UNIVERSITY_REG_LABEL = "University Reg No"

private fun getProfileMap(doc: Document): Map<String, String> {
    /*
    val batch = doc.select("center span a").text()
    val photoLink = doc.select("img#photo").attr("src")

    if (batch.isNotEmpty()) {
        //profileMap["batch"] = batch
    }

    if (photoLink.isNotEmpty()) {
        //profileMap["photo"] = NetworkUrl.HOME + photoLink.substring(1)
    }*/

    val profileMap = doc.select(
        ":is(:contains(personal details), :contains(parent details)) + div tbody tr"
    ).map {
        it.select("th").text() to it.select("td").text().capitalizeText()
    }.filter { it.second.isNotEmpty() }.toMap().toMutableMap()


    profileMap[UNIVERSITY_REG_LABEL]?.let {
        profileMap [UNIVERSITY_REG_LABEL] = it.uppercase()
    }

    // TODO - do email decryption to fix "[email protected]" value

    return profileMap
}

private fun String.capitalizeText(): String {
    return this
        .split(' ')
        .joinToString(" ") { it.lowercase().replaceFirstChar(Char::uppercase) }
}
