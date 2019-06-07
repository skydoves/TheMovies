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
package com.skydoves.themovies.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.skydoves.themovies.api.ApiResponse
import com.skydoves.themovies.mappers.NetworkResponseMapper
import com.skydoves.themovies.models.NetworkResponseModel
import com.skydoves.themovies.models.Resource
import timber.log.Timber

/**
 * Created by skydoves on 2018. 8. 9.
 * Copyright (c) 2018 skydoves All rights reserved.
 */

abstract class NetworkBoundRepository<ResultType,
  RequestType : NetworkResponseModel,
  Mapper : NetworkResponseMapper<RequestType>>
internal constructor() {

  private val result: MediatorLiveData<Resource<ResultType>> = MediatorLiveData()

  init {
    Timber.d("Injection NetworkBoundRepository")
    val loadedFromDB = this.loadFromDb()
    result.addSource(loadedFromDB) { data ->
      result.removeSource(loadedFromDB)
      if (shouldFetch(data)) {
        result.postValue(Resource.loading(null))
        fetchFromNetwork(loadedFromDB)
      } else {
        result.addSource<ResultType>(loadedFromDB) { newData ->
          setValue(Resource.success(newData, false))
        }
      }
    }
  }

  private fun fetchFromNetwork(loadedFromDB: LiveData<ResultType>) {
    val apiResponse = fetchService()
    result.addSource(apiResponse) { response ->
      response?.let {
        when (response.isSuccessful) {
          true -> {
            response.body?.let {
              saveFetchData(it)
              val loaded = loadFromDb()
              result.addSource(loaded) { newData ->
                newData?.let {
                  setValue(Resource.success(newData, mapper().onLastPage(response.body)))
                }
              }
            }
          }
          false -> {
            result.removeSource(loadedFromDB)
            onFetchFailed(response.message)
            response.message?.let {
              result.addSource<ResultType>(loadedFromDB) { newData ->
                setValue(Resource.error(it, newData))
              }
            }
          }
        }
      }
    }
  }

  @MainThread
  private fun setValue(newValue: Resource<ResultType>) {
    result.value = newValue
  }

  fun asLiveData(): LiveData<Resource<ResultType>> {
    return result
  }

  @WorkerThread
  protected abstract fun saveFetchData(items: RequestType)

  @MainThread
  protected abstract fun shouldFetch(data: ResultType?): Boolean

  @MainThread
  protected abstract fun loadFromDb(): LiveData<ResultType>

  @MainThread
  protected abstract fun fetchService(): LiveData<ApiResponse<RequestType>>

  @MainThread
  protected abstract fun mapper(): Mapper

  @MainThread
  protected abstract fun onFetchFailed(message: String?)
}
