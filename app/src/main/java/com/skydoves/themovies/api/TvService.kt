package com.skydoves.themovies.api

import android.arch.lifecycle.LiveData
import com.skydoves.themovies.models.network.KeywordListResponse
import com.skydoves.themovies.models.network.ReviewListResponse
import com.skydoves.themovies.models.network.VideoListResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Developed by skydoves on 2018-08-20.
 * Copyright (c) 2018 skydoves rights reserved.
 */

interface TvService {
    /**
     * [Tv Videos](https://developers.themoviedb.org/3/tv/get-tv-keywords)
     *
     * Get the keywords that have been added to a TV show.
     *
     * @param [id] Specify the id of tv keywords.
     *
     * @return [VideoListResponse] response
     */
    @GET("/3/tv/{tv_id}/keywords")
    fun fetchKeywords(@Path("tv_id") id: Int): LiveData<ApiResponse<KeywordListResponse>>

    /**
     * [Tv Videos](https://developers.themoviedb.org/3/tv/get-tv-videos)
     *
     * Get the videos that have been added to a TV show.
     *
     * @param [id] Specify the id of tv id.
     *
     * @return [VideoListResponse] response
     */
    @GET("/3/tv/{tv_id}/videos")
    fun fetchVideos(@Path("tv_id") id: Int): LiveData<ApiResponse<VideoListResponse>>

    /**
     * [Tv Reviews](https://developers.themoviedb.org/3/tv/get-tv-reviews)
     *
     * Get the reviews for a TV show.
     *
     * @param [id] Specify the id of tv id.
     *
     * @return [ReviewListResponse] response
     */
    @GET("/3/tv/{tv_id}/reviews")
    fun fetchReviews(@Path("tv_id") id: Int): LiveData<ApiResponse<ReviewListResponse>>

}
