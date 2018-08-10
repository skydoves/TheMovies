package com.skydoves.themovies.api

import com.skydoves.themovies.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Developed by skydoves on 2018-08-11.
 * Copyright (c) 2018 skydoves rights reserved.
 */

internal class RequestInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url()
        val url = originalUrl.newBuilder()
                .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
                .build()

        val requestBuilder = originalRequest.newBuilder().url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
