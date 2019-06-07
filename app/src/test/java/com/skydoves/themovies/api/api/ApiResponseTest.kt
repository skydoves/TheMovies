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

import com.skydoves.themovies.api.ApiResponse
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@RunWith(JUnit4::class)
class ApiResponseTest {

  @Test
  fun exception() {
    val exception = Exception("foo")
    val apiResponse = ApiResponse<String>(exception)
    assertThat(apiResponse.isSuccessful, `is`(false))
    assertThat<String>(apiResponse.body, CoreMatchers.nullValue())
    assertThat(apiResponse.code, `is`(500))
    assertThat(apiResponse.message, `is`("foo"))
  }

  @Test
  fun success() {
    val apiResponse = ApiResponse(Response.success("foo"))
    assertThat(apiResponse.isSuccessful, `is`(true))
    assertThat(apiResponse.code, `is`(200))
    assertThat<String>(apiResponse.body, `is`("foo"))
    assertThat(apiResponse.message, CoreMatchers.nullValue())
  }
}
