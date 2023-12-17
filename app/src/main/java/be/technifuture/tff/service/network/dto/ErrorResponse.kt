package be.technifuture.tff.service.network.dto

data class ErrorDetailsResponse(
    val details: String
)

data class ErrorRegisterResponse(
    val username: List<String>?,
    val email: List<String>?,
    val password: List<String>?
)

data class ErrorMessageResponse(
    val message: String
)