package com.skydoves.themovies.models.network

import com.skydoves.themovies.models.NetworkResponseModel
import com.skydoves.themovies.models.entity.Person

/**
 * Developed by skydoves on 2018-08-15.
 * Copyright (c) 2018 skydoves rights reserved.
 */

data class PeopleResponse(val page: Int,
                          val results: List<Person>,
                          val total_results: Int,
                          val total_pages: Int): NetworkResponseModel
