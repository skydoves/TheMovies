package com.skydoves.themovies.models.network

import com.skydoves.themovies.models.entity.Movie
import com.skydoves.themovies.models.NetworkResponseModel

/**
 * Developed by skydoves on 2018-08-08.
 * Copyright (c) 2018 skydoves rights reserved.
 */

data class DiscoverMovieResponse(val page: Int,
                                 val results: List<Movie>,
                                 val total_results: Int,
                                 val total_pages: Int): NetworkResponseModel
