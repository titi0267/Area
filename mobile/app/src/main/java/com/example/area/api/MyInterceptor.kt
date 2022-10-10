package com.example.area.api

import com.example.area.utils.SessionManager
import okhttp3.Interceptor
import okhttp3.Response

class MyInterceptor (): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("X-Platform", "Android")
            .build()
        return (chain.proceed(request))
    }
}