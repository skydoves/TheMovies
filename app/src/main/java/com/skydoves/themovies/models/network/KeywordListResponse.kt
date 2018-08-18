package com.skydoves.themovies.models.network

import com.skydoves.themovies.models.Keyword
import com.skydoves.themovies.models.NetworkResponseModel

/**
 * Developed by skydoves on 2018-08-17.
 * Copyright (c) 2018 skydoves rights reserved.
 */

data class KeywordListResponse(val id: Int,
                               val keywords: List<Keyword>): NetworkResponseModel
