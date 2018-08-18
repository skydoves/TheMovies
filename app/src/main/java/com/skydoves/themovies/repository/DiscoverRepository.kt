package com.skydoves.themovies.repository

import android.arch.lifecycle.LiveData
import com.skydoves.themovies.api.ApiResponse
import com.skydoves.themovies.api.TheDiscoverService
import com.skydoves.themovies.mappers.MovieResponseMapper
import com.skydoves.themovies.mappers.TvResponseMapper
import com.skydoves.themovies.models.*
import com.skydoves.themovies.models.entity.Movie
import com.skydoves.themovies.models.entity.Tv
import com.skydoves.themovies.models.network.DiscoverMovieResponse
import com.skydoves.themovies.models.network.DiscoverTvResponse
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
constructor(val discoverService: TheDiscoverService, val movieDao: MovieDao, val tvDao: TvDao)
    : Repository {

    init {
        Timber.d("Injection DiscoverRepository")
    }

    fun loadMovies(page: Int): LiveData<Resource<List<Movie>>> {
        return object : NetworkBoundRepository<List<Movie>, DiscoverMovieResponse, MovieResponseMapper>() {
            override fun saveFetchData(items: DiscoverMovieResponse) {
                for(item in items.results) {
                    item.page = page
                }
                movieDao.insertMovieList(movies = items.results)
            }

            override fun shouldFetch(data: List<Movie>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<Movie>> {
                return movieDao.getMovieList(page_ = page)
            }

            override fun fetchService(): LiveData<ApiResponse<DiscoverMovieResponse>> {
                return discoverService.fetchDiscoverMovie(page = page)
            }

            override fun mapper(): MovieResponseMapper {
                return MovieResponseMapper()
            }

            override fun onFetchFailed(message: String?) {
                Timber.d("onFetchFailed $message")
            }
        }.asLiveData()
    }

    fun loadTvs(page: Int): LiveData<Resource<List<Tv>>> {
        return object : NetworkBoundRepository<List<Tv>, DiscoverTvResponse, TvResponseMapper>() {
            override fun saveFetchData(items: DiscoverTvResponse) {
                for(item in items.results) {
                    item.page = page
                }
                tvDao.insertTv(tvs = items.results)
            }

            override fun shouldFetch(data: List<Tv>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<Tv>> {
                return tvDao.getTvList(page_ = page)
            }

            override fun fetchService(): LiveData<ApiResponse<DiscoverTvResponse>> {
                return discoverService.fetchDiscoverTv(page = page)
            }

            override fun mapper(): TvResponseMapper {
                return TvResponseMapper()
            }

            override fun onFetchFailed(message: String?) {
                Timber.d("oFetchFailed $message")
            }
        }.asLiveData()
    }
}
