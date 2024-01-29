package devendrn.ecb.client.network

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import org.jsoup.Connection
import org.jsoup.Jsoup

private const val USER_AGENT = "Mozilla"
private const val TIMEOUT_MS = 20000
const val SESSION_ID = "TKMSESSIONID"

class NetworkClient {
    val activityUrl: MutableStateFlow<String?> = MutableStateFlow(null)

    fun get(
        url: String,
        timeout: Int = TIMEOUT_MS,
        sessionId: String? = null
    ): Connection.Response {
        Log.d("NETWORK CLIENT","GET REQUEST: $url")
        activityUrl.value = url.substringAfter(NetworkUrl.DOMAIN)

        val conn = Jsoup.connect(url)
            .userAgent(USER_AGENT)
            .timeout(timeout)
            .method(Connection.Method.GET)

        if (sessionId != null) {
            conn.cookie(SESSION_ID, sessionId)
        }

        val response = conn.execute()

        activityUrl.value = null
        return response
    }

    fun post(
        url: String,
        data: Map<String, String>,
        timeout: Int = TIMEOUT_MS,
        sessionId: String? = null
    ): Connection.Response? {
        Log.d("NETWORK CLIENT","POST REQUEST: $url WITH: $data")
        activityUrl.value = url.substringAfter(NetworkUrl.DOMAIN)

        val conn = Jsoup.connect(url)
            .userAgent(USER_AGENT)
            .timeout(timeout)
            .method(Connection.Method.POST)

        data.forEach {
            conn.data(it.key, it.value)
        }

        if (sessionId != null) {
            conn.cookie(SESSION_ID, sessionId)
        }

        val response = try {
            conn.execute()
        } catch (e: Exception) {  // network error
            null
        }

        activityUrl.value = null
        return response
    }
}
