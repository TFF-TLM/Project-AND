package be.technifuture.tff.service.network.interceptor

import be.technifuture.tff.BuildConfig
import be.technifuture.tff.service.network.utils.UrlApi
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor (): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        request.addHeader(UrlApi.headerKeyMainApi, BuildConfig.API_KEY)
        return chain.proceed(request.build())
    }
}