package com.skydoves.themovies.models.network

import com.skydoves.themovies.models.NetworkResponseModel
import com.skydoves.themovies.models.Video

/**
 * Developed by skydoves on 2018-08-18.
 * Copyright (c) 2018 skydoves rights reserved.
 */

data class VideoListResponse(val id: Int,
                             val results: List<Video>): NetworkResponseModel
