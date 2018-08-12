package com.skydoves.themovies.models

/**
 * Developed by skydoves on 2018-08-08.
 * Copyright (c) 2018 skydoves rights reserved.
 */

data class ResponseModel(val page: Int,
                         val results: Any,
                         val total_results: Int,
                         val total_pages: Int)
