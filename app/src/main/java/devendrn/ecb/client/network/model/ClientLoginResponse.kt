package devendrn.ecb.client.network.model

enum class ClientLoginResponse {
    SUCCESS, CAPTCHA_REQUIRED, INVALID_USERNAME, INVALID_PASSWORD, INVALID_CAPTCHA, NETWORK_ERROR
}