package devendrn.ecb.client.network.scrapper.parser

import devendrn.ecb.client.network.NetworkManager
import devendrn.ecb.client.network.NetworkUrl
import org.jsoup.nodes.Document

const val TIMETABLE_URL = "${NetworkUrl.HOME}student/timetable"

fun NetworkManager.getTimetable(): Map<String, List<String>> {
    return getTimetable(getPage(TIMETABLE_URL))
}
private fun getTimetable(doc: Document): Map<String, List<String>> {
    val timetable = doc.select("div#timetable tr").map { row ->
        row.select("td").map { item ->
            val text = item.text().substringBefore(" [ ").substringAfter(" - ")
            if (text != "Free Period") text else ""
        }
    }.filter {
        it.isNotEmpty()
    }.filter {
        it.first() != "Saturday"
    }.associate {
        it.first() to it.subList(1, 7)
    }

    return timetable
}