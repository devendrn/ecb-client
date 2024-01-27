package devendrn.ecb.client.network.model

enum class NetworkLoginResponse {
    SUCCESS, CAPTCHA_REQUIRED, INVALID_USERNAME, INVALID_PASSWORD, INVALID_CAPTCHA, NETWORK_ERROR
}