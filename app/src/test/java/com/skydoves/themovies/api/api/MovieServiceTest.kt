package com.skydoves.themovies.api.api

import com.skydoves.themovies.api.MovieService
import com.skydoves.themovies.utils.LiveDataTestUtil
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import java.io.IOException

/**
 * Developed by skydoves on 2018-08-17.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class MovieServiceTest: ApiAbstract<MovieService>() {

    private lateinit var service: MovieService

    @Before fun initService() {
        this.service = createService(MovieService::class.java)
    }

    @Throws(IOException::class)
    @Test fun fetchMovieKeywordsTest() {
        enqueueResponse("/tmdb_movie_keywords.json")
        val response = LiveDataTestUtil.getValue(service.fetchKeywords(1))
        assertThat(response.body?.id, `is`(550))
        assertThat(response.body?.keywords?.get(0)?.id, `is`(825))
        assertThat(response.body?.keywords?.get(0)?.name, `is`("support group"))
    }
}
