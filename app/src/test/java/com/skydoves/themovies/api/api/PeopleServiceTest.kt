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

import com.skydoves.themovies.api.PeopleService
import com.skydoves.themovies.utils.LiveDataTestUtil
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import java.io.IOException

class PeopleServiceTest : ApiAbstract<PeopleService>() {

  private lateinit var service: PeopleService

  @Before
  fun initService() {
    this.service = createService(PeopleService::class.java)
  }

  @Throws(IOException::class)
  @Test
  fun fetchPersonListTest() {
    enqueueResponse("/tmdb_people.json")
    val response = LiveDataTestUtil.getValue(service.fetchPopularPeople(1))
    assertThat(response.body?.results?.get(0)?.id, `is`(28782))
    assertThat(response.body?.total_pages, `is`(984))
    assertThat(response.body?.total_results, `is`(19671))
  }

  @Throws(IOException::class)
  @Test
  fun fetchPersonDetail() {
    enqueueResponse("tmdb_person.json")
    val response = LiveDataTestUtil.getValue(service.fetchPersonDetail(123))
    assertThat(response.body?.birthday, `is`("1963-12-18"))
    assertThat(response.body?.known_for_department, `is`("Acting"))
    assertThat(response.body?.place_of_birth, `is`("Shawnee, Oklahoma, USA"))
  }
}
