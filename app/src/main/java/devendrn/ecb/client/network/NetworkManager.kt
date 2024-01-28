package devendrn.ecb.client.network

import android.util.Log
import devendrn.ecb.client.data.PASSWORD
import devendrn.ecb.client.data.SESSION
import devendrn.ecb.client.data.USERNAME
import devendrn.ecb.client.database.dao.UserDao
import devendrn.ecb.client.database.model.UserEntity
import devendrn.ecb.client.network.model.NetworkCredential
import devendrn.ecb.client.network.model.NetworkLoginResponse
import org.jsoup.nodes.Document

class NetworkManager(
    private val userDao: UserDao
) {
    private val client: NetworkClient = NetworkClient()
    private var loginCredential: NetworkCredential = NetworkCredential("", "", "")

    private fun updateSessionDatabase(username: String, password: String, sessionId: String) {
        userDao.upsert(UserEntity(0, USERNAME, username))
        userDao.upsert(UserEntity(0, PASSWORD, password))
        userDao.upsert(UserEntity(0, SESSION, sessionId))
    }
    private fun clearSessionDatabase() {
        userDao.clearAll()
    }

    private fun updateSession(username: String, password: String, sessionId: String) {
        loginCredential = NetworkCredential(
            username = username,
            password = password,
            sessionId = sessionId
        )
    }

    fun retrieveSession() {
        loginCredential = NetworkCredential(
            username = userDao.read(USERNAME)?: "",
            password = userDao.read(PASSWORD)?: "",
            sessionId = userDao.read(SESSION)?: ""
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
        ) ?: return NetworkLoginResponse.NETWORK_ERROR

        // login will redirect if successful
        val url = res.url().toString()
        if (url == NetworkUrl.HOME) {
            val sessionId = res.cookie(SESSION_ID)?: ""
            updateSession(username, password, sessionId)
            updateSessionDatabase(username, password, sessionId)
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
            clearSessionDatabase()
        }

        return isSuccess
    }
}