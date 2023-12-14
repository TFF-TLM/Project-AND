package be.technifuture.tff.service.network.service

import be.technifuture.tff.service.network.authenticator.AuthAuthenticator
import be.technifuture.tff.service.network.dto.UserDataRequestBody
import be.technifuture.tff.service.network.dto.UserDataResponse
import be.technifuture.tff.service.network.dto.UserDetailsResponse
import be.technifuture.tff.service.network.dto.UserInfoResponse
import be.technifuture.tff.service.network.dto.UserResponse
import be.technifuture.tff.service.network.interceptor.ApiKeyInterceptor
import be.technifuture.tff.service.network.interceptor.AuthInterceptor
import be.technifuture.tff.service.network.utils.ClientBuilder
import be.technifuture.tff.service.network.utils.ServiceBuilder
import be.technifuture.tff.service.network.utils.UrlApi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApiService {
    @GET("user")
    suspend fun listUser(): Response<List<UserResponse>>

    @GET("user/{user_id}")
    suspend fun user(@Path("user_id") id: String): Response<UserResponse>

    @GET("user/info")
    suspend fun listUserInfo(): Response<List<UserInfoResponse>>

    @GET("user/info/{user_id}")
    suspend fun userInfo(@Path("user_id") id: String): Response<UserInfoResponse>

    @GET("user/details")
    suspend fun listUserDetails(): Response<List<UserDetailsResponse>>

    @GET("user/details/{user_id}")
    suspend fun userDetails(@Path("user_id") id: String): Response<UserDetailsResponse>

    @GET("user/data")
    suspend fun listUserData(): Response<List<UserDataResponse>>

    @GET("user/data/{user_id}")
    suspend fun userData(@Path("user_id") id: String): Response<UserDataResponse>

    @POST("user/data/")
    suspend fun createUserData(
        @Body data: UserDataRequestBody,
    ): Response<UserDataResponse>
}

class UserApiServiceImpl {
    private val service: UserApiService

    companion object {
        val instance: UserApiServiceImpl by lazy {
            UserApiServiceImpl()
        }
    }
    init {
        val client = ClientBuilder.buildClient(
            listOf(ApiKeyInterceptor(), AuthInterceptor()),
            AuthAuthenticator()
        )
        service = ServiceBuilder.buildService(UserApiService::class.java, UrlApi.mainApi, client)
    }
    suspend fun listUser(): Response<List<UserResponse>> = service.listUser()

    suspend fun user(id: Int): Response<UserResponse> = service.user(id.toString())

    suspend fun listUserInfo(): Response<List<UserInfoResponse>> = service.listUserInfo()

    suspend fun userInfo(id: Int): Response<UserInfoResponse> = service.userInfo(id.toString())

    suspend fun listUserDetails(): Response<List<UserDetailsResponse>> = service.listUserDetails()

    suspend fun userDetails(id: Int): Response<UserDetailsResponse> = service.userDetails(id.toString())

    suspend fun listUserData(): Response<List<UserDataResponse>> = service.listUserData()

    suspend fun userData(id: Int): Response<UserDataResponse> = service.userData(id.toString())

    suspend fun createUserData(data: UserDataRequestBody): Response<UserDataResponse> = service.createUserData(data)

}