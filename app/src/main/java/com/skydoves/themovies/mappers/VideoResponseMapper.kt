package com.skydoves.themovies.mappers

import com.skydoves.themovies.models.network.VideoListResponse

/**
 * Developed by skydoves on 2018-08-17.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class VideoResponseMapper: NetworkResponseMapper<VideoListResponse> {
    override fun onLastPage(response: VideoListResponse): Boolean {
        return true
    }
}
