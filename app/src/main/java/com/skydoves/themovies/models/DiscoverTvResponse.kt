package com.skydoves.themovies.models

/**
 * Developed by skydoves on 2018-08-07.
 * Copyright (c) 2018 skydoves rights reserved.
 */

data class DiscoverTvResponse(val page: Int,
                              val results: List<Tv>,
                              val total_results: Int,
                              val total_pages: Int)