package be.technifuture.tff.service.network.authenticator

import be.technifuture.tff.service.network.dto.RefreshToken
import be.technifuture.tff.service.network.dto.RefreshTokenResponse
import be.technifuture.tff.service.network.manager.AuthDataManager
import be.technifuture.tff.service.network.service.AuthApiServiceImpl
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class AuthAuthenticator : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val refreshToken = AuthDataManager.instance.getRefreshToken()
        return runBlocking {
            val newToken = getNewToken(refreshToken)
            newToken.body()?.let {
                AuthDataManager.instance.accessToken = it.access
                response.request.newBuilder()
                    .header("Authorization", "Bearer ${it.access}")
                    .build()
            }
        }
    }

    private suspend fun getNewToken(refreshToken: String?): retrofit2.Response<RefreshTokenResponse> {
        val service = AuthApiServiceImpl.instance
        return service.refresh(RefreshToken("$refreshToken"))
    }
}