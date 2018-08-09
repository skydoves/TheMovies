package com.skydoves.themovies.repository

import android.arch.lifecycle.LiveData
import com.skydoves.themovies.BuildConfig
import com.skydoves.themovies.api.ApiResponse
import com.skydoves.themovies.api.TheDiscoverService
import com.skydoves.themovies.models.DiscoverMovieResponse
import com.skydoves.themovies.models.Movie
import com.skydoves.themovies.models.Resource
import com.skydoves.themovies.room.MovieDao
import com.skydoves.themovies.room.TvDao
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Developed by skydoves on 2018-08-09.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@Singleton
class DiscoverRepository @Inject
constructor(val discoverService: TheDiscoverService, val movieDao: MovieDao, val tvDao: TvDao) {

    init {
        Timber.d("Injection DiscoverRepository")
    }

    fun loadMovies(page: Int): LiveData<Resource<List<Movie>>> {
        return object : NetworkBoundRepository<List<Movie>, DiscoverMovieResponse>() {
            override fun saveFetchData(items: DiscoverMovieResponse) {
                for(item in items.results) {
                    item.page = page
                }
                movieDao.insertMovie(movies = items.results)
            }

            override fun shouldFetch(data: List<Movie>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<Movie>> {
                return movieDao.getMovieList(page_ = page)
            }

            override fun fetchService(): LiveData<ApiResponse<DiscoverMovieResponse>> {
                return discoverService.fetchDiscoverMovie(apiKey = BuildConfig.TMDB_API_KEY, page = page)
            }

            override fun onFetchFailed(message: String?) {
                Timber.d("onFetchFailed $message")
            }
        }.asLiveData()
    }
}
