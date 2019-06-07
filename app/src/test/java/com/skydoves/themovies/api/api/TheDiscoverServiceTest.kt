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
package com.skydoves.themovies.api.api

import com.skydoves.themovies.api.TheDiscoverService
import com.skydoves.themovies.utils.LiveDataTestUtil
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import java.io.IOException

class TheDiscoverServiceTest : ApiAbstract<TheDiscoverService>() {

  private lateinit var service: TheDiscoverService

  @Before
  fun initService() {
    this.service = createService(TheDiscoverService::class.java)
  }

  @Throws(IOException::class)
  @Test
  fun fetchMovieListTest() {
    enqueueResponse("/tmdb_movie.json")
    val response = LiveDataTestUtil.getValue(service.fetchDiscoverMovie(1))
    assertThat(response.body?.results?.get(0)?.id, `is`(164558))
    assertThat(response.body?.total_results, `is`(61))
    assertThat(response.body?.total_pages, `is`(4))
  }

  @Throws(IOException::class)
  @Test
  fun fetchTvListTest() {
    enqueueResponse("/tmdb_tv.json")
    val response = LiveDataTestUtil.getValue(service.fetchDiscoverTv(1))
    assertThat(response.body?.results?.get(0)?.id, `is`(61889))
    assertThat(response.body?.total_results, `is`(61470))
    assertThat(response.body?.total_pages, `is`(3074))
  }
}
