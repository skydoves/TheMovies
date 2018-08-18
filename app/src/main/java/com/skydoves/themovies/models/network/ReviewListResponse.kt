package com.skydoves.themovies.models.network

import com.skydoves.themovies.models.NetworkResponseModel
import com.skydoves.themovies.models.Review

/**
 * Developed by skydoves on 2018-08-18.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class ReviewListResponse(val id: Int,
                         val page: Int,
                         val results: List<Review>,
                         val total_pages: Int,
                         val total_results: Int): NetworkResponseModel
