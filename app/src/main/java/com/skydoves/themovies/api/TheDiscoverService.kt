package com.skydoves.themovies.api

import android.arch.lifecycle.LiveData
import android.support.annotation.NonNull
import com.skydoves.themovies.models.DiscoverMovieResponse
import com.skydoves.themovies.models.DiscoverTvResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Developed by skydoves on 2018-08-07.
 * Copyright (c) 2018 skydoves rights reserved.
 */

interface TheDiscoverService {
    @GET("/3/discover/movie")
    fun fetchDiscoverMovie(@Query("api_key") @NonNull apiKey: String, @Query("page") page: Int): LiveData<ApiResponse<DiscoverMovieResponse>>

    @GET("/3/discover/tv")
    fun fetchDiscoverTv(@Query("api_key") @NonNull apiKey: String, @Query("page") page: Int): LiveData<ApiResponse<DiscoverTvResponse>>
}
