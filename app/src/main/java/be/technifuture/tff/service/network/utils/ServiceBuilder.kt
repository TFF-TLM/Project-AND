package be.technifuture.tff.service.network.utils

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    fun <T> buildService(service: Class<T>, urlApi: String, client: OkHttpClient): T {
        return Retrofit.Builder()
            .baseUrl(urlApi)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(service)
    }
}