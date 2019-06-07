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

import retrofit2.Response
import timber.log.Timber
import java.io.IOException

@Suppress("MemberVisibilityCanBePrivate")
class ApiResponse<T> {
  val code: Int
  val body: T?
  val message: String?

  val isSuccessful: Boolean
    get() = code in 200..300
  val isFailure: Boolean

  constructor(error: Throwable) {
    this.code = 500
    this.body = null
    this.message = error.message
    this.isFailure = true
  }

  constructor(response: Response<T>) {
    this.code = response.code()

    if (response.isSuccessful) {
      this.body = response.body()
      this.message = null
      this.isFailure = false
    } else {
      var errorMessage: String? = null
      response.errorBody()?.let {
        try {
          errorMessage = response.errorBody()!!.string()
        } catch (ignored: IOException) {
          Timber.e(ignored, "error while parsing response")
        }
      }

      errorMessage?.apply {
        if (isNullOrEmpty() || trim { it <= ' ' }.isEmpty()) {
          errorMessage = response.message()
        }
      }

      this.body = null
      this.message = errorMessage
      this.isFailure = true
    }
  }
}
