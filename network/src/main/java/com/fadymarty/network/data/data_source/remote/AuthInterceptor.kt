package com.fadymarty.network.data.data_source.remote

import com.apollographql.apollo.api.http.HttpRequest
import com.apollographql.apollo.api.http.HttpResponse
import com.apollographql.apollo.network.http.HttpInterceptor
import com.apollographql.apollo.network.http.HttpInterceptorChain
import com.fadymarty.network.domain.manager.TokenManager
import kotlinx.coroutines.flow.first

class AuthInterceptor(
    private val tokenManager: TokenManager,
) : HttpInterceptor {

    override suspend fun intercept(
        request: HttpRequest,
        chain: HttpInterceptorChain,
    ): HttpResponse {
        val token = tokenManager.getToken().first()
        return chain.proceed(
            request = request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        )
    }
}