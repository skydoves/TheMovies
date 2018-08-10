package com.skydoves.themovies.api.api

import com.skydoves.themovies.api.TheDiscoverService
import com.skydoves.themovies.utils.LiveDataTestUtil
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import java.io.IOException

/**
 * Developed by skydoves on 2018-08-07.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class TheDiscoverServiceTest: ApiAbstract<TheDiscoverService>() {

    private lateinit var service: TheDiscoverService

    @Before fun initService() {
        this.service = createService(TheDiscoverService::class.java)
    }

    @Throws(IOException::class)
    @Test fun fetchMovieListTest() {
        enqueueResponse("/tmdb_movie.json")
        val response = LiveDataTestUtil.getValue(service.fetchDiscoverMovie(1))
        assertThat(response.body?.results?.get(0)?.id, `is`(164558))
        assertThat(response.body?.total_results, `is`(61))
        assertThat(response.body?.total_pages, `is`(4))
    }

    @Throws(IOException::class)
    @Test fun fetchTvListTest() {
        enqueueResponse("/tmdb_tv.json")
        val response = LiveDataTestUtil.getValue(service.fetchDiscoverTv(1))
        assertThat(response.body?.results?.get(0)?.id, `is`(61889))
        assertThat(response.body?.total_results, `is`(61470))
        assertThat(response.body?.total_pages, `is`(3074))
    }
}
