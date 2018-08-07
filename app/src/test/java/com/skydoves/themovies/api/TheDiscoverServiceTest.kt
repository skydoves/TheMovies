package com.skydoves.themovies.api

import com.skydoves.themovies.BuildConfig
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

class TheDiscoverServiceTest: APITest<TheDiscoverService>() {

    private lateinit var service: TheDiscoverService

    @Before fun initService() {
        this.service = createService(TheDiscoverService::class.java)
    }

    @Throws(IOException::class)
    @Test fun tvTest() {
        enqueueResponse("/tmdb_tv.json")
        val response = LiveDataTestUtil.getValue(service.fetchDiscover(BuildConfig.TMDB_API_KEY))
        assertThat(response.body?.total_results, `is`(74881L))
        assertThat(response.body?.total_pages, `is`(3745L))
    }
}
