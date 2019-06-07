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
import com.skydoves.themovies.models.network.PeopleResponse
import com.skydoves.themovies.models.network.PersonDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PeopleService {
  /**
   * [People Popular](https://developers.themoviedb.org/3/people/get-popular-people)
   *
   * Get the list of popular people on TMDb. This list updates daily.
   *
   * @param [page] Specify the page of results to query.
   *
   * @return [PeopleResponse] response
   */
  @GET("/3/person/popular?language=en")
  fun fetchPopularPeople(@Query("page") page: Int): LiveData<ApiResponse<PeopleResponse>>

  /**
   * [Person Detail](https://developers.themoviedb.org/3/people/get-person-details)
   *
   * Get the primary person details by id.
   *
   * @para [id] Specify the id of results to query.
   *
   * @return [PersonDetail] response
   */
  @GET("/3/person/{person_id}")
  fun fetchPersonDetail(@Path("person_id") id: Int): LiveData<ApiResponse<PersonDetail>>
}
