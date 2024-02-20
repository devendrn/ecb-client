package devendrn.ecb.client.network

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import devendrn.ecb.client.data.PASSWORD
import devendrn.ecb.client.data.SESSION
import devendrn.ecb.client.data.USERNAME
import devendrn.ecb.client.database.dao.UserDao
import devendrn.ecb.client.database.model.UserEntity
import devendrn.ecb.client.network.api.KtuResultApi
import devendrn.ecb.client.network.model.NetworkCredential
import devendrn.ecb.client.network.model.NetworkLoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.jsoup.nodes.Document
import java.text.SimpleDateFormat

class NetworkManager(
    private val context: Context,
    private val userDao: UserDao
) {
    private val client: NetworkClient = NetworkClient(context)
    private var loginCredential: NetworkCredential = NetworkCredential("", "", "")

    val activity = client.activityUrl
    val isOnline = client.isOnline

    val ktuApi = KtuResultApi()

    @SuppressLint("SimpleDateFormat")
    val lastUpdateTimeString: Flow<String> = client.lastUpdateTime.map { time ->
        SimpleDateFormat("hh:mm a MMM dd").format(time)
    }

    private fun updateSession(username: String, password: String, sessionId: String) {
        loginCredential = NetworkCredential(
            username = username,
            password = password,
            sessionId = sessionId
        )
        userDao.upsert(UserEntity(USERNAME, username))
        userDao.upsert(UserEntity(PASSWORD, password))
        userDao.upsert(UserEntity(SESSION, sessionId))
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
            Log.d("NETWORK MANAGER","GET PAGE: SESSION EXPIRED!")

            when(login(loginCredential.username, loginCredential.password)) {
                NetworkLoginResponse.SUCCESS -> Log.d("NETWORK MANAGER", "AUTO LOGIN SUCCESS")
                NetworkLoginResponse.NETWORK_ERROR -> Log.d("NETWORK MANAGER", "AUTO LOGIN FAILED - NETWORK ERROR")
                else -> Log.d("NETWORK MANAGER", "AUTO LOGIN FAILED - INVALIDATED CREDENTIAL")
            }
        }

        return res.parse()
    }

    fun login(
        username: String,
        password: String,
        //captcha: String? = null
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
        updateSession("", "", "")

        val res = client.get(
            url = NetworkUrl.USER_LOGOUT,
            sessionId = loginCredential.sessionId
        )

        // logout will redirect to login page if successful
        return res.url().toString() == NetworkUrl.USER_LOGIN
    }
}