package com.skydoves.themovies.models

/**
 * Developed by skydoves on 2018-08-07.
 * Copyright (c) 2018 skydoves rights reserved.
 */

data class Poster(val poster_path: String,
                  val popularity: Float,
                  val id: Long,
                  val backdrop_path: String?,
                  val vote_average: Float,
                  val overview: String,
                  val first_air_date: String,
                  val origin_country: List<String>,
                  val genre_ids: List<Long>,
                  val original_language: String,
                  val vote_count: Int,
                  val name: String,
                  val original_name: String)
