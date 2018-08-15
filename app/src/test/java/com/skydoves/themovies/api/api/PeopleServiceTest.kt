package com.skydoves.themovies.api.api

import com.skydoves.themovies.api.PeopleService
import com.skydoves.themovies.utils.LiveDataTestUtil
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import java.io.IOException

/**
 * Developed by skydoves on 2018-08-15.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class PeopleServiceTest: ApiAbstract<PeopleService>() {

    private lateinit var service: PeopleService

    @Before fun initService() {
        this.service = createService(PeopleService::class.java)
    }

    @Throws(IOException::class)
    @Test fun fetchPersonListTest() {
        enqueueResponse("/tmdb_people.json")
        val response = LiveDataTestUtil.getValue(service.fetchPopularPeople(1))
        assertThat(response.body?.results?.get(0)?.id,`is`(28782))
        assertThat(response.body?.total_pages, `is`(984))
        assertThat(response.body?.total_results, `is`(19671))
    }
}
