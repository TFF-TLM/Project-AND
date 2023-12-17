package be.technifuture.tff.service.network.service

import be.technifuture.tff.service.network.authenticator.AuthAuthenticator
import be.technifuture.tff.service.network.dto.InteractCatResponse
import be.technifuture.tff.service.network.dto.InteractInterestPointResponse
import be.technifuture.tff.service.network.dto.SurroundingsResponse
import be.technifuture.tff.service.network.interceptor.ApiKeyInterceptor
import be.technifuture.tff.service.network.interceptor.AuthInterceptor
import be.technifuture.tff.service.network.utils.ClientBuilder
import be.technifuture.tff.service.network.utils.ServiceBuilder
import be.technifuture.tff.service.network.utils.UrlApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface InteractApiService {
    @GET("surroundings")
    suspend fun surroundings(
        @Query("lat") lat: String,
        @Query("lon") lon: String
    ): Response<SurroundingsResponse>

    @GET("interact/cat")
    suspend fun interactCat(
        @Query("id") id: String,
        @Query("food") food: String
    ): Response<InteractCatResponse>

    @GET("interact/interest")
    suspend fun interactInterest(
        @Query("id") id: String
    ): Response<InteractInterestPointResponse>
}

class InteractApiServiceImpl {
    private val service: InteractApiService

    companion object {
        val instance: InteractApiServiceImpl by lazy {
            InteractApiServiceImpl()
        }
    }

    init {
        val client = ClientBuilder.buildClient(
            listOf(ApiKeyInterceptor(), AuthInterceptor()),
            AuthAuthenticator()
        )
        service =
            ServiceBuilder.buildService(InteractApiService::class.java, UrlApi.mainApi, client)
    }

    suspend fun surroundings(lat: Float, lon: Float): Response<SurroundingsResponse> =
        service.surroundings(lat.toString(), lon.toString())

    suspend fun interactCat(id: Int, food: Int): Response<InteractCatResponse> =
        service.interactCat(id.toString(), food.toString())

    suspend fun interactInterest(id: Int): Response<InteractInterestPointResponse> =
        service.interactInterest(id.toString())
}