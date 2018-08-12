package com.skydoves.themovies.api

/**
 * Developed by skydoves on 2018-08-12.
 * Copyright (c) 2018 skydoves rights reserved.
 */

object Api {
    private const val BASE_POSTER_PATH = "http://image.tmdb.org/t/p/w342"
    private const val BASE_BACKDROP_PATH = "http://image.tmdb.org/t/p/w780"

    fun getPosterPath(posterPath: String): String {
        return BASE_POSTER_PATH + posterPath
    }

    fun getBackdropPath(backdropPath: String): String {
        return BASE_BACKDROP_PATH + backdropPath
    }
}
