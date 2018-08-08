package com.skydoves.themovies.models

import android.arch.persistence.room.Entity

/**
 * Developed by skydoves on 2018-08-07.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@Entity(primaryKeys = [("id")])
data class Tv(val poster_path: String,
              val popularity: Float,
              val id: Int,
              val backdrop_path: String?,
              val vote_average: Float,
              val overview: String,
              val first_air_date: String,
              val origin_country: List<String>,
              val genre_ids: List<Int>,
              val original_language: String,
              val vote_count: Int,
              val name: String,
              val original_name: String)
