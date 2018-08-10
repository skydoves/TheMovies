package com.skydoves.themovies.api.api

import com.skydoves.themovies.api.ApiResponse
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

/**
 * Developed by skydoves on 2018-08-10.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@RunWith(JUnit4::class)
class ApiResponseTest {

    @Test fun exception() {
        val exception = Exception("foo")
        val apiResponse = ApiResponse<String>(exception)
        assertThat(apiResponse.isSuccessful, `is`(false))
        assertThat<String>(apiResponse.body, CoreMatchers.nullValue())
        assertThat(apiResponse.code, `is`(500))
        assertThat(apiResponse.message, `is`("foo"))
    }

    @Test fun success() {
        val apiResponse = ApiResponse(Response.success("foo"))
        assertThat(apiResponse.isSuccessful, `is`(true))
        assertThat(apiResponse.code, `is`(200))
        assertThat<String>(apiResponse.body, `is`("foo"))
        assertThat(apiResponse.message, CoreMatchers.nullValue())
    }
}
