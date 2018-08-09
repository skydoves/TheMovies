package com.skydoves.themovies.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import com.skydoves.themovies.api.ApiResponse
import com.skydoves.themovies.models.Resource
import timber.log.Timber

/**
 * Created by skydoves on 2018. 3. 6.
 * Copyright (c) 2018 skydoves All rights reserved.
 */

abstract class NetworkBoundRepository<ResultType, RequestType>
internal constructor() {

    private val result: MediatorLiveData<Resource<ResultType>> = MediatorLiveData()

    init {
        Timber.d("Injection NetworkBoundRepository")
        result.postValue(Resource.loading(null))
        val loadedFromDB = loadFromDb()
        result.addSource(loadedFromDB) { data ->
            result.removeSource(loadedFromDB)
            if (shouldFetch(data)) {
                fetchFromNetwork(loadedFromDB)
            } else {
                result.addSource<ResultType>(loadedFromDB) { newData -> setValue(Resource.success(newData)) }
            }
        }
    }

    private fun fetchFromNetwork(loadedFromDB: LiveData<ResultType>) {
        val apiResponse = fetchService()
        result.addSource(loadedFromDB) { newData -> setValue(Resource.loading(newData))}
        result.addSource(apiResponse) { response ->
            response?.let {
                when(response.isSuccessful) {
                    true -> {
                        response.body?.let {
                            saveFetchData(it)
                            val loaded = loadFromDb()
                            result.addSource(loaded) { newData ->
                                newData?.let {
                                    setValue(Resource.success(newData))
                                }
                            }
                        }
                    }
                    false -> {
                        result.removeSource(loadedFromDB)
                        onFetchFailed(response.message)
                        response.message?.let {
                            result.addSource<ResultType>(loadedFromDB) {
                                newData -> setValue(Resource.error(it, newData)) }
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
    protected abstract fun onFetchFailed(message: String?)
}
