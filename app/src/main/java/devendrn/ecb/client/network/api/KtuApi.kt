package devendrn.ecb.client.network.api

import com.google.gson.JsonParser
import devendrn.ecb.client.model.KtuAnnouncement
import devendrn.ecb.client.model.KtuAnnouncementAttachment
import devendrn.ecb.client.model.KtuExam
import devendrn.ecb.client.model.KtuExamResult
import org.jsoup.Connection
import org.jsoup.Jsoup
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

private const val USER_AGENT = "Mozilla"
private const val TIMEOUT_MS = 8000
private const val KTU_WEB_SERVICE = "https://api.ktu.edu.in/ktu-web-service/anon"
private const val KTU_WEB_PORTAL = "https://api.ktu.edu.in/ktu-web-portal-api/anon"
class KtuResultApi {
    private fun postJson(
        url: String,
        json: String,
        timeout: Int = TIMEOUT_MS
    ): Connection.Response {
        val conn = Jsoup.connect(url).sslSocketFactory(SSLHelperKotlin.socketFactory())
            .userAgent(USER_AGENT)
            .timeout(timeout)
            .header("content-type", "application/json")
            .header("accept", "application/json")
            .requestBody(json)
            .ignoreContentType(true)
            .method(Connection.Method.POST)

        return conn.execute()
    }

    internal fun parseExamResults(json: String): List<KtuExamResult> {
        val jsonObj = JsonParser.parseString(json).asJsonObject

        val results = jsonObj.get("resultDetails").asJsonArray.map { result ->
            result.asJsonObject.asMap()
                .filter { item ->
                    !item.value.isJsonNull
                }
                .map {
                    it.key.toString() to it.value.asString
                }.toMap()
                .filter {
                    it.key in listOf("courseName", "grade")
                }
        }

        return results.map {
            KtuExamResult(
                name = it["courseName"]?:"",
                grade = it["grade"]?:""
            )
        }
    }

    internal fun parseExams(json: String): List<KtuExam> {
        val jsonObj = JsonParser.parseString(json).asJsonArray

        val courses: List<Map<String, String>> = jsonObj.map { entry ->
            entry.asJsonObject.asMap().filter {
                !it.value.isJsonNull
            }.map {
                it.key to it.value.asString
            }.toMap()
        }

        return courses.map {
            KtuExam(
                name = it["resultName"]?:"Unknown",
                examDefId = (it["examDefId"]?.toInt())?: 0,
                schemeId = (it["schemeId"]?.toInt())?: 0,
                //publishDate = it["publishDate"]?:""
            )
        }
    }

    internal fun parseAnnouncements(json: String): List<KtuAnnouncement> {
        val jsonObj = JsonParser.parseString(json).asJsonObject

        val announcements = jsonObj.get("content").asJsonArray.map { announcement ->
            val item = announcement.asJsonObject

            val messageObj = item.get("message")
            val message = if (messageObj.isJsonNull) "" else messageObj.asString

            val date = item.get("announcementDate").asString?: ""

            val attachments = item.get("attachmentList").asJsonArray.map {
                val attachment = it.asJsonObject
                KtuAnnouncementAttachment(
                    title = attachment.get("title")?.asString?: "",
                    name = attachment.get("attachmentName")?.asString?: "",
                    encryptId = ""
                )
            }

            KtuAnnouncement(
                title = item.get("subject").asString?: "",
                message = message,
                date = date.substringBefore(" "),
                attachments = attachments
            )
        }

        return announcements.sortedByDescending {
            it.date
        }
    }

    fun getExams(): List<KtuExam> {
        val res = postJson(
            url = "$KTU_WEB_SERVICE/result",
            json = """{"program": 1}"""
        )
        return parseExams(res.body())
    }

    fun getExamResults(
        regNo: String,
        dob: String,
        examDefId: Int,
        schemeId: Int
    ): List<KtuExamResult> {
        // getExamResults expects dob in DD/MM/YYYY
        // api needs dob in YYYY-MM-DD
        val dobFormatted = dob.split("/").reversed().joinToString("-")
        val res = postJson(
            url = "$KTU_WEB_SERVICE/individualresult",
            json = """{"registerNo":"$regNo","dateOfBirth":"$dobFormatted","examDefId":$examDefId,"schemeId":$schemeId}"""
        )
        return parseExamResults(res.body())
    }

    fun getAnnouncements(
        searchText: String,
        size: Int,
        pageNo: Int
    ): List<KtuAnnouncement> {
        val res = postJson(
            url = "$KTU_WEB_PORTAL/announcemnts",
            json = """{"number":$pageNo,"searchText":"$searchText","size":$size}"""
        )
        return parseAnnouncements(res.body())
    }
}

// To disable certificate validation
// https://gist.github.com/sezabass/6a95deee8656571ce7046f3adc2ba573#file-sslhelperkotlin-kt-L19
private class SSLHelperKotlin {
    companion object {
        @JvmStatic
        fun socketFactory(): SSLSocketFactory {
            val trustAllCerts = arrayOf<TrustManager>(
                object : X509TrustManager {
                    override fun getAcceptedIssuers() = arrayOf<X509Certificate>()
                    override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) {}
                    override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) {}
                }
            )

            return try {
                val sslContext: SSLContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, SecureRandom())
                sslContext.socketFactory
            } catch (e: NoSuchAlgorithmException) {
                throw RuntimeException("Failed to create a SSL socket factory", e)
            } catch (e: KeyManagementException) {
                throw RuntimeException("Failed to create a SSL socket factory", e)
            }
        }
    }
}