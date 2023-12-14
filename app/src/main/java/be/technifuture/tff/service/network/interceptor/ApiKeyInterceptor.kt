package be.technifuture.tff.service.network.interceptor

import be.technifuture.tff.service.network.utils.UrlApi
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor (): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        request.addHeader(UrlApi.headerKeyMainApi, UrlApi.mainApiKey)
        return chain.proceed(request.build())
    }
}