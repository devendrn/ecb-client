package devendrn.ecb.client.network.scrapper.parser

import devendrn.ecb.client.network.NetworkManager
import devendrn.ecb.client.network.NetworkUrl
import org.jsoup.nodes.Document

const val PROFILE_URL = "${NetworkUrl.HOME}student/profile"

fun NetworkManager.getProfileData(): Map<String, String> {
    return getProfileMap(getPage(PROFILE_URL))
}

private fun getProfileMap(doc: Document): Map<String, String> {
    val profileMap = mutableMapOf<String, String>()

    val batch = doc.select("center span a").text()
    val photoLink = doc.select("img#photo").attr("src")

    if (batch.isNotEmpty()) {
        profileMap["batch"] = batch
    }

    if (photoLink.isNotEmpty()) {
        profileMap["photo"] = NetworkUrl.HOME + photoLink.substring(1)
    }

    val profile = doc.select(":is(table#yw0 tr, table#yw1 tr)")
    profile.forEach {
        val key = it.select("th").text()
            .lowercase()
            .replace(" ", "_")
        profileMap[key] = it.select("td").text()
    }

    return profileMap
}