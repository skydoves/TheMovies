/*
 * The MIT License (MIT)
 *
 * Designed and developed by 2018 skydoves (Jaewoong Eum)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.skydoves.themovies.db

import androidx.test.runner.AndroidJUnit4
import com.skydoves.themovies.models.entity.Movie
import com.skydoves.themovies.utils.LiveDataTestUtil
import com.skydoves.themovies.utils.MockTestUtil.Companion.mockMovie
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieDaoTest : DbTest() {

  @Test
  fun insertAndReadTest() {
    val movieList = ArrayList<Movie>()
    val movie = mockMovie()
    movieList.add(movie)

    db.movieDao().insertMovieList(movieList)
    val loadFromDB = LiveDataTestUtil.getValue(db.movieDao().getMovieList(movie.page))[0]
    assertThat(loadFromDB.page, `is`(1))
    assertThat(loadFromDB.id, `is`(123))
  }

  @Test
  fun updateAndReadTest() {
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
