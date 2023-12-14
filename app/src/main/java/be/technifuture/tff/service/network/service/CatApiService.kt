package be.technifuture.tff.service.network.service

import be.technifuture.tff.service.network.authenticator.AuthAuthenticator
import be.technifuture.tff.service.network.dto.CatInBagResponse
import be.technifuture.tff.service.network.dto.CatOnMapResponse
import be.technifuture.tff.service.network.dto.DropCatRequestBody
import be.technifuture.tff.service.network.dto.DropCatResponse
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

interface CatApiService {
    @GET("cat/user/bag")
    suspend fun catInBag(): Response<CatInBagResponse>

    @GET("cat/user/map")
    suspend fun catOnMap(): Response<CatOnMapResponse>

    @POST("cat/drop/")
    suspend fun dropCat(
        @Body data: DropCatRequestBody,
    ): Response<DropCatResponse>
}

class CatApiServiceImpl {
    private val service: CatApiService

    companion object {
        val instance: CatApiServiceImpl by lazy {
            CatApiServiceImpl()
        }
    }
    init {
        val client = ClientBuilder.buildClient(
            listOf(ApiKeyInterceptor(), AuthInterceptor()),
            AuthAuthenticator()
        )
        service = ServiceBuilder.buildService(CatApiService::class.java, UrlApi.mainApi, client)
    }

    suspend fun catInBag(): Response<CatInBagResponse> = service.catInBag()

    suspend fun catOnBMap(): Response<CatOnMapResponse> = service.catOnMap()

    suspend fun dropCat(data: DropCatRequestBody): Response<DropCatResponse> = service.dropCat(data)
}