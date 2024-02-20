package devendrn.ecb.client.network

import android.content.Context
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import org.jsoup.Connection
import org.jsoup.Jsoup
import java.util.Calendar
import java.util.Date

private const val USER_AGENT = "Mozilla"
private const val TIMEOUT_MS = 20000
const val SESSION_ID = "TKMSESSIONID"

class NetworkClient(
    context: Context,
) {
    val isOnline = NetworkStatus(context).isOnline

    val activityUrl: MutableStateFlow<String?> = MutableStateFlow(null)

    // TODO - store last update time in user database
    val lastUpdateTime: MutableStateFlow<Date> = MutableStateFlow(
        Calendar.getInstance().time
    )

    private fun updateLastTime() {
        lastUpdateTime.value = Calendar.getInstance().time
    }

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

        try {
            val response = conn.execute()
            updateLastTime()
            activityUrl.value = null
            return response
        } catch (e: Exception) {  // network error
            activityUrl.value = null
            throw e
        }
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

        if (response != null) updateLastTime()
        activityUrl.value = null
        return response
    }
}