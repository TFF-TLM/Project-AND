package be.technifuture.tff.service.network.utils

import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object ClientBuilder {
    fun buildClient(interceptors: List<Interceptor>, authenticator: Authenticator? = null): OkHttpClient {
        val okBuilder = OkHttpClient().newBuilder().apply {
            connectTimeout(60, TimeUnit.SECONDS)
            callTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
        }

        interceptors.forEach {
            okBuilder.addInterceptor(it)
        }

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okBuilder.addInterceptor(loggingInterceptor)

        authenticator?.let {
            okBuilder.authenticator(it)
        }

        return okBuilder.build()
    }
}