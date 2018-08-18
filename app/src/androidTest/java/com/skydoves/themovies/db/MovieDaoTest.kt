package com.skydoves.themovies.db

import android.support.test.runner.AndroidJUnit4
import com.skydoves.themovies.models.entity.Movie
import com.skydoves.themovies.utils.LiveDataTestUtil
import com.skydoves.themovies.utils.MockTestUtil.Companion.mockMovie
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Developed by skydoves on 2018-08-10.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@RunWith(AndroidJUnit4::class)
class MovieDaoTest: DbTest() {

    @Test fun insertAndReadTest() {
        val movieList = ArrayList<Movie>()
        val movie = mockMovie()
        movieList.add(movie)

        db.movieDao().insertMovieList(movieList)
        val loadFromDB = LiveDataTestUtil.getValue(db.movieDao().getMovieList(movie.page))[0]
        assertThat(loadFromDB.page, `is`(1))
        assertThat(loadFromDB.id, `is`(123))
    }

    @Test fun updateAndReadTest() {
        val movieList = ArrayList<Movie>()
        val movie = mockMovie()
        movieList.add(movie)
        db.movieDao().insertMovieList(movieList)

        val loadFromDB = db.movieDao().getMovie(movie.id)
        assertThat(loadFromDB.page, `is`(1))

        movie.page = 10
        db.movieDao().updateMovie(movie)

        val updated = db.movieDao().getMovie(movie.id)
        assertThat(updated.page, `is`(10))
    }
}
