package com.skydoves.themovies.models

/**
 * Developed by skydoves on 2018-08-15.
 * Copyright (c) 2018 skydoves rights reserved.
 */

data class PeopleResponse(val page: Int,
                          val results: List<Person>,
                          val total_results: Int,
                          val total_pages: Int): NetworkResponseModel
