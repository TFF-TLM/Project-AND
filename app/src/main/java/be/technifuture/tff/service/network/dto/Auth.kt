package be.technifuture.tff.service.network.dto


data class Auth(
    val email: String,
    val password: String
)

data class AuthResponse(
    val refresh: String,
    val access: String,
    val user: UserResponse
)

data class VerificationResponse(
    val available: Boolean
)

data class Register(
    val username: String,
    val email: String,
    val password: String
)

data class RefreshToken(
    val refresh: String
)

data class RefreshTokenResponse(
    val access: String
)