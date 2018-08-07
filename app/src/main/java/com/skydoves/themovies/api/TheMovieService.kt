package com.skydoves.themovies.api

import android.arch.lifecycle.LiveData
import com.skydoves.themovies.models.DiscoverResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Developed by skydoves on 2018-08-07.
 * Copyright (c) 2018 skydoves rights reserved.
 */

interface TheMovieService {
    @GET("/3/discover/tv")
    fun fetchDiscover(@Query("api_key") apiKey: String): LiveData<ApiResponse<DiscoverResponse>>
}
