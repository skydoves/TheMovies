package com.skydoves.themovies.api.api

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.skydoves.themovies.api.ApiResponse
import retrofit2.Response

/**
 * Developed by skydoves on 2018-08-10.
 * Copyright (c) 2018 skydoves rights reserved.
 */

object ApiUtil {
    fun <T : Any> successCall(data: T) = createCall(Response.success(data))

    private fun <T : Any> createCall(response: Response<T>) = MutableLiveData<ApiResponse<T>>().apply {
        value = ApiResponse(response)
    } as LiveData<ApiResponse<T>>
}