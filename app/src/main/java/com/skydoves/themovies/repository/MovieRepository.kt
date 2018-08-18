package com.skydoves.themovies.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.skydoves.themovies.api.ApiResponse
import com.skydoves.themovies.api.MovieService
import com.skydoves.themovies.mappers.KeywordResponseMapper
import com.skydoves.themovies.models.Keyword
import com.skydoves.themovies.models.Resource
import com.skydoves.themovies.models.network.KeywordListResponse
import com.skydoves.themovies.room.MovieDao
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Developed by skydoves on 2018-08-17.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@Singleton
class MovieRepository @Inject
constructor(val service: MovieService, val movieDao: MovieDao): Repository {

    init {
        Timber.d("Injection MovieRepository")
    }

    fun loadKeywordList(id: Int): LiveData<Resource<List<Keyword>>> {
        return object: NetworkBoundRepository<List<Keyword>, KeywordListResponse, KeywordResponseMapper>() {
            override fun saveFetchData(items: KeywordListResponse) {
                val movie = movieDao.getMovie(id)
                movie.keywords = items.keywords
                movieDao.updateMovie(movie = movie)
            }

            override fun shouldFetch(data: List<Keyword>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<Keyword>> {
                val movie = movieDao.getMovie(id_ = id)
                val data: MutableLiveData<List<Keyword>> = MutableLiveData()
                data.value = movie.keywords
                return data
            }

            override fun fetchService(): LiveData<ApiResponse<KeywordListResponse>> {
                return service.fetchKeywords(id = id)
            }

            override fun mapper(): KeywordResponseMapper {
                return KeywordResponseMapper()
            }

            override fun onFetchFailed(message: String?) {
                Timber.d("onFetchFailed : $message")
            }
        }.asLiveData()
    }
}
