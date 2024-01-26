package devendrn.ecb.client.network

object NetworkUrl {
    private const val DOMAIN = "tkmce.etlab.in"
    const val HOME = "https://$DOMAIN/"
    const val USER_LOGIN = "${HOME}user/login"
    const val USER_LOGOUT = "${HOME}user/logout"
}