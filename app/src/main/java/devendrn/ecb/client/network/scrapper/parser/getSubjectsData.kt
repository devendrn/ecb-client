package devendrn.ecb.client.network.scrapper.parser

import devendrn.ecb.client.network.NetworkManager
import devendrn.ecb.client.network.NetworkUrl
import devendrn.ecb.client.network.scrapper.model.Fraction
import devendrn.ecb.client.network.scrapper.model.Subject
import org.jsoup.nodes.Document

const val SUBJECTS_URL = "${NetworkUrl.HOME}student/subject"

fun NetworkManager.getSubjectsData(): Map<String, Subject> {
    return getSubjectsData(getPage(SUBJECTS_URL))
}

private fun getSubjectsData(doc: Document): Map<String, Subject> {
    val subjects = mutableMapOf<String, Subject>()

    /* TODO - save this number */
    val semesterNo = when (
        doc.select("select#sem_id [selected]").text()
            .substringBefore(" ")
    ) {
        "Ist" -> 1
        "IInd" -> 2
        "IIIrd" -> 3
        "IVth" -> 4
        "Vth" -> 5
        "VIth" -> 6
        "VIIth" -> 7
        "VIIIth" -> 8
        else -> 0
    }

    val subjectRows = doc.select("tbody tr")
    subjectRows.forEach {
        val description = it.child(1).text()
        val subjectName = description
            .substringAfter("- ")
            .capitalizeAllWords()
            .replace("And ", "and ")
        val attendanceString = it.child(4).text()
            .substringBefore("(")
        val key = description
            .substringBefore("-")
            .replace(" ", "")
        subjects[key] = Subject(
            name = subjectName,
            abbr = subjectName
                .replace("For", "O")
                .replace(Regex("[a-z ]"), ""),
            teacher = it.child(3).text()
                .replace(".", " ")
                .capitalizeAllWords(),
            attendance = Fraction(
                attendanceString
                    .substringBefore("/")
                    .toInt(),
                attendanceString
                    .substringAfter("/")
                    .toInt()
            ),
            pageId = it.child(1).child(0).attr("href")
                .substringAfterLast("/")
                .toInt()
        )
    }

    return subjects
}

private fun String.capitalizeAllWords(): String {
    return this.lowercase().split(" ").joinToString(" ") {
        it.replaceFirstChar(Char::uppercaseChar)
    }
}
