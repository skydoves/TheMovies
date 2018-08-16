package com.skydoves.themovies.mappers

import com.skydoves.themovies.models.network.DiscoverMovieResponse
import timber.log.Timber

/**
 * Developed by skydoves on 2018-08-12.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class MovieResponseMapper: NetworkResponseMapper<DiscoverMovieResponse> {
    override fun onLastPage(response: DiscoverMovieResponse): Boolean {
        Timber.d("loadPage : ${response.page}/${response.total_pages}")
        return response.page > response.total_pages
    }
}
