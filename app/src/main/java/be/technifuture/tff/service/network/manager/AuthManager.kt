package be.technifuture.tff.service.network.manager

import be.technifuture.tff.service.network.dto.UserResponse


class AuthDataManager {
    var refreshToken: String? = null
    var accessToken: String? = null

    companion object {
        val instance: AuthDataManager by lazy {
            AuthDataManager()
        }
    }
}