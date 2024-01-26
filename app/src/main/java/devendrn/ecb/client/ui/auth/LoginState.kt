package devendrn.ecb.client.ui.auth

data class LoginState(
    val username: String = "",
    val password: String = "",
    val isChecking: Boolean = false,
    val invalidUsername: Boolean = false,
    val invalidPassword: Boolean = false
)
