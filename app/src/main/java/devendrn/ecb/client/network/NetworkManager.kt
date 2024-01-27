package devendrn.ecb.client.network

import android.util.Log
import devendrn.ecb.client.database.dao.UserDao
import devendrn.ecb.client.network.model.NetworkCredential
import devendrn.ecb.client.network.model.NetworkLoginResponse
import org.jsoup.nodes.Document

class NetworkManager(
    private val userDao: UserDao
) {
    private var loginCredential: NetworkCredential

    private val client: NetworkClient = NetworkClient()

    init {
        loginCredential = retrieveSession()
    }

    // TODO - use dao to update/fetch session details

    private fun retrieveSession(): NetworkCredential {
        /*
        return ClientCredential(
            username = userDao.read(USERNAME),
            password = userDao.read(PASSWORD),
            sessionId = userDao.read(SESSION)
        )*/

        return NetworkCredential(
            username = "",
            password = "",
            sessionId = ""
        )
    }

    private fun updateSession(username: String, password: String, sessionId: String) {
        loginCredential = NetworkCredential(
            username = username,
            password = password,
            sessionId = sessionId
        )
    }

    fun isLoggedIn(): Boolean {
        return loginCredential.username.isNotEmpty()
    }

    fun getPage(url: String): Document {
        val res = client.get(
            url = url,
            sessionId = loginCredential.sessionId
        )

        // check if login is required
        if (res.url().toString() == NetworkUrl.USER_LOGIN) {
            /* TODO  Handle login */
        }  else {

        }

        return res.parse()
    }

    fun login(
        username: String,
        password: String,
        captcha: String? = null
    ): NetworkLoginResponse {
        val res = client.post(
            url = NetworkUrl.USER_LOGIN,
            data = mapOf(
                "LoginForm[username]" to username,
                "LoginForm[password]" to password,
            )
        )

        // login will redirect if successful
        val url = res.url().toString()
        if (url == NetworkUrl.HOME) {
            updateSession(username, password, res.cookie(SESSION_ID))
            return NetworkLoginResponse.SUCCESS
        } else {
            val form = res.parse().body()
            Log.w("LOGIN ERROR",form.toString())
            return when {
                form.select("#LoginForm_username_em_").hasText() -> {
                    NetworkLoginResponse.INVALID_USERNAME
                }
                form.select("#LoginForm_password_em_").hasText() -> {
                    NetworkLoginResponse.INVALID_PASSWORD
                }
                form.select("#LoginForm_captcha_em_").hasText() -> {
                    NetworkLoginResponse.INVALID_CAPTCHA
                }
                else -> {
                    // never encountered this case in testing for some reason
                    NetworkLoginResponse.CAPTCHA_REQUIRED
                }
            }
        }
    }

    fun logout(): Boolean {
        val res = client.get(
            url = NetworkUrl.USER_LOGOUT,
            sessionId = loginCredential.sessionId
        )

        // logout will redirect to login page if successful
        val isSuccess = res.url().toString() == NetworkUrl.USER_LOGIN

        if (isSuccess) {
            // TODO - Clear user table
            updateSession("", "", "")
        }

        return isSuccess
    }
}