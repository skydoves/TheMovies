package com.skydoves.themovies.mappers

import com.skydoves.themovies.models.network.PeopleResponse
import timber.log.Timber

/**
 * Developed by skydoves on 2018-08-15.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class PeopleResponseMapper: NetworkResponseMapper<PeopleResponse> {
    override fun onLastPage(response: PeopleResponse): Boolean {
        Timber.d("loadPage : ${response.page}/${response.total_pages}")
        return response.page > response.total_pages
    }
}
