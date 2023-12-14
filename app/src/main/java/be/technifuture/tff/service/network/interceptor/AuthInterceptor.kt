package be.technifuture.tff.service.network.interceptor

import be.technifuture.tff.service.network.manager.AuthDataManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor (): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = AuthDataManager.instance.accessToken
        val request = chain.request().newBuilder()
        request.addHeader("Authorization", "Bearer $token")
        return chain.proceed(request.build())
    }
}