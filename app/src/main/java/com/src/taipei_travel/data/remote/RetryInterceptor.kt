package com.src.taipei_travel.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class RetryInterceptor(private val maxRetries: Int = 3) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = chain.proceed(request)

        var attempt: Int = response.request.headers[RETRY_ATTEMP]?.toInt() ?: 1
        while (!response.isSuccessful && attempt < maxRetries) {
            attempt++
            val newRequest = chain.request().newBuilder().addHeader(RETRY_ATTEMP, "$attempt").build()
            response.close()
            response = chain.proceed(newRequest)
        }
        return response
    }

    companion object {
        private const val RETRY_ATTEMP = "retryAttempt"
    }
}
