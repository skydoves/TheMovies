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
package com.skydoves.themovies.api

import androidx.lifecycle.LiveData
import com.skydoves.themovies.models.network.DiscoverMovieResponse
import com.skydoves.themovies.models.network.DiscoverTvResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TheDiscoverService {
  /**
   * [Movie Discover](https://developers.themoviedb.org/3/discover/movie-discover)
   *
   *  Discover movies by different types of data like average rating, number of votes, genres and certifications.
   *  You can get a valid list of certifications from the  method.
   *
   *  @param [page] Specify the page of results to query.
   *
   *  @return [DiscoverMovieResponse] response
   */
  @GET("/3/discover/movie?language=en&sort_by=popularity.desc")
  fun fetchDiscoverMovie(@Query("page") page: Int): LiveData<ApiResponse<DiscoverMovieResponse>>

  /**
   * [Tv Discover](https://developers.themoviedb.org/3/discover/tv-discover)
   *
   *  Discover TV shows by different types of data like average rating, number of votes, genres, the network they aired on and air dates.
   *
   *  @param [page] Specify the page of results to query.
   *
   *  @return [DiscoverTvResponse] response
   */
  @GET("/3/discover/tv?language=en&sort_by=popularity.desc")
  fun fetchDiscoverTv(@Query("page") page: Int): LiveData<ApiResponse<DiscoverTvResponse>>
}
