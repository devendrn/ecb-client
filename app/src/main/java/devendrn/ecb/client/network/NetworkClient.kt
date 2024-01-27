package devendrn.ecb.client.network

import org.jsoup.Connection
import org.jsoup.Jsoup

private const val USER_AGENT = "Mozilla"
private const val TIMEOUT_MS = 20000
const val SESSION_ID = "TKMSESSIONID"

class NetworkClient {
    fun get(
        url: String,
        timeout: Int = TIMEOUT_MS,
        sessionId: String? = null
    ): Connection.Response {
        val conn = Jsoup.connect(url)
            .userAgent(USER_AGENT)
            .timeout(timeout)
            .method(Connection.Method.GET)

        if (sessionId != null) {
            conn.cookie(SESSION_ID, sessionId)
        }

        return conn.execute()
    }

    fun post(
        url: String,
        data: Map<String, String>,
        timeout: Int = TIMEOUT_MS,
        sessionId: String? = null
    ): Connection.Response {
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

        return conn.execute()
    }
}
