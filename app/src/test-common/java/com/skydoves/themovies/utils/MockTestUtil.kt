package com.skydoves.themovies.utils

import com.skydoves.themovies.models.Movie
import com.skydoves.themovies.models.Tv

/**
 * Developed by skydoves on 2018-08-10.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class MockTestUtil {
    companion object {
        fun mockMovie() = Movie(1, "", false, "", "", ArrayList(), 123, "", "", "", "", 0f, 0, false, 0f)
        fun mockTv() = Tv(1, "", 0f, 123, "", 0f, "", "", ArrayList(), ArrayList(), "", 1, "", "")
    }
}
