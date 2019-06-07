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
package com.skydoves.themovies.models

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.skydoves.themovies.models.network.ErrorEnvelope

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE", "LiftReturnOrAssignment", "RedundantOverride", "SpellCheckingInspection")
class Resource<out T>(val status: Status, val data: T?, val message: String?, val onLastPage: Boolean) {

  var errorEnvelope: ErrorEnvelope? = null

  init {
    message?.let {
      try {
        val gson = Gson()
        errorEnvelope = gson.fromJson(message, ErrorEnvelope::class.java) as ErrorEnvelope
      } catch (e: JsonSyntaxException) {
        errorEnvelope = ErrorEnvelope(400, message, false)
      }
    }
  }

  override fun equals(o: Any?): Boolean {
    if (this === o) {
      return true
    }
    if (o == null || javaClass != o.javaClass) {
      return false
    }

    val resource = o as Resource<*>

    if (status !== resource.status) {
      return false
    }
    if (if (message != null) message != resource.message else resource.message != null) {
      return false
    }
    return if (data != null) data == resource.data else resource.data == null
  }

  override fun hashCode(): Int {
    return super.hashCode()
  }

  override fun toString(): String {
    return "Resource[" +
      "status=" + status + '\'' +
      ",message='" + message + '\'' +
      ",data=" + data +
      ']'
  }

  companion object {
    fun <T> success(data: T?, onLastPage: Boolean): Resource<T> {
      return Resource(status = Status.SUCCESS, data = data, message = null, onLastPage = false)
    }

    fun <T> error(msg: String, data: T?): Resource<T> {
      return Resource(status = Status.ERROR, data = data, message = msg, onLastPage = true)
    }

    fun <T> loading(data: T?): Resource<T> {
      return Resource(status = Status.LOADING, data = data, message = null, onLastPage = false)
    }
  }
}
