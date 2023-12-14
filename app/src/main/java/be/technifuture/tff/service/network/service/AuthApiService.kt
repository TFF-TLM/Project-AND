package be.technifuture.tff.service.network.service

import retrofit2.http.Body
import retrofit2.http.POST
import be.technifuture.tff.service.network.dto.Auth
import be.technifuture.tff.service.network.dto.AuthResponse
import be.technifuture.tff.service.network.dto.RefreshToken
import be.technifuture.tff.service.network.dto.RefreshTokenResponse
import be.technifuture.tff.service.network.dto.Register
import be.technifuture.tff.service.network.dto.VerificationResponse
import be.technifuture.tff.service.network.interceptor.ApiKeyInterceptor
import be.technifuture.tff.service.network.utils.ClientBuilder
import be.technifuture.tff.service.network.utils.ServiceBuilder
import be.technifuture.tff.service.network.utils.UrlApi
import retrofit2.Response

interface AuthApiService {
    @POST("auth/login/")
    suspend fun login(
        @Body auth: Auth,
    ): Response<AuthResponse>

    @POST("auth/verification/")
    suspend fun verification(
        @Body register: Register,
    ): Response<VerificationResponse>

    @POST("auth/register/")
    suspend fun register(
        @Body register: Register,
    ): Response<AuthResponse>

    @POST("auth/refresh/")
    suspend fun refreshToken(
        @Body refresh: RefreshToken
    ): Response<RefreshTokenResponse>
}

class AuthApiServiceImpl {
    private val service: AuthApiService

    companion object {
        val instance:AuthApiServiceImpl by lazy {
            AuthApiServiceImpl()
        }
    }
    init {
        val client = ClientBuilder.buildClient(listOf(ApiKeyInterceptor()))
        service = ServiceBuilder.buildService(AuthApiService::class.java, UrlApi.mainApi, client)
    }
    suspend fun login(auth: Auth): Response<AuthResponse> = service.login(auth)

    suspend fun verification(register: Register): Response<VerificationResponse> = service.verification(register)

    suspend fun register(register: Register): Response<AuthResponse> = service.register(register)

    suspend fun refresh(refresh: RefreshToken): Response<RefreshTokenResponse> = service.refreshToken(refresh)
}
