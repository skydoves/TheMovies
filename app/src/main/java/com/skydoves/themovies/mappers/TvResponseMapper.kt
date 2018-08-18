package com.skydoves.themovies.mappers

import com.skydoves.themovies.models.network.DiscoverTvResponse
import timber.log.Timber

/**
 * Developed by skydoves on 2018-08-13.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class TvResponseMapper: NetworkResponseMapper<DiscoverTvResponse> {
    override fun onLastPage(response: DiscoverTvResponse): Boolean {
        Timber.d("loadPage : ${response.page}/${response.total_pages}")
        return response.page > response.total_pages
    }
}
