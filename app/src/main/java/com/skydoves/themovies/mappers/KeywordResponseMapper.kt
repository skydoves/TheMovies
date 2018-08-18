package com.skydoves.themovies.mappers

import com.skydoves.themovies.models.network.KeywordListResponse

/**
 * Developed by skydoves on 2018-08-17.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class KeywordResponseMapper: NetworkResponseMapper<KeywordListResponse> {
    override fun onLastPage(response: KeywordListResponse): Boolean {
        return true
    }
}
