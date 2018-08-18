package com.skydoves.themovies.mappers

import com.skydoves.themovies.models.network.ReviewListResponse

/**
 * Developed by skydoves on 2018-08-18.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class ReviewResponseMapper: NetworkResponseMapper<ReviewListResponse> {
    override fun onLastPage(response: ReviewListResponse): Boolean {
        return true
    }
}
