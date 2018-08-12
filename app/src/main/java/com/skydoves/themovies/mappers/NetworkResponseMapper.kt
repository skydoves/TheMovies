package com.skydoves.themovies.mappers

import com.skydoves.themovies.models.NetworkResponseModel

/**
 * Developed by skydoves on 2018-08-12.
 * Copyright (c) 2018 skydoves rights reserved.
 */

interface NetworkResponseMapper<in FROM: NetworkResponseModel> {
    fun onLastPage(response: FROM): Boolean
}
