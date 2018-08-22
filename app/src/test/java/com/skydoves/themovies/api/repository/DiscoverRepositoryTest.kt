package com.skydoves.themovies.api.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.skydoves.themovies.api.TheDiscoverService
import com.skydoves.themovies.api.api.ApiUtil.successCall
import com.skydoves.themovies.models.*
import com.skydoves.themovies.models.entity.Movie
import com.skydoves.themovies.models.entity.Tv
import com.skydoves.themovies.models.network.DiscoverMovieResponse
import com.skydoves.themovies.models.network.DiscoverTvResponse
import com.skydoves.themovies.repository.DiscoverRepository
import com.skydoves.themovies.room.MovieDao
import com.skydoves.themovies.room.TvDao
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Developed by skydoves on 2018-08-10.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@RunWith(JUnit4::class)
class DiscoverRepositoryTest {

    private lateinit var repository: DiscoverRepository
    private val movieDao = mock<MovieDao>()
    private val tvDao = mock<TvDao>()
    private val service = mock<TheDiscoverService>()

    @Rule @JvmField val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        repository = DiscoverRepository(service, movieDao, tvDao)
    }

    @Test
    fun loadMovieListFromNetwork() {
        val loadFromDB = MutableLiveData<List<Movie>>()
        whenever(movieDao.getMovieList(1)).thenReturn(loadFromDB)

        val mockResponse = DiscoverMovieResponse(1, emptyList(), 100, 10)
        val call = successCall(mockResponse)
        whenever(service.fetchDiscoverMovie(1)).thenReturn(call)

        val data = repository.loadMovies(1)
        verify(movieDao).getMovieList(1)
        verifyNoMoreInteractions(service)

        val observer = mock<Observer<Resource<List<Movie>>>>()
        data.observeForever(observer)
        verifyNoMoreInteractions(service)
        val updatedData = MutableLiveData<List<Movie>>()
        whenever(movieDao.getMovieList(1)).thenReturn(updatedData)

        loadFromDB.postValue(null)
        verify(observer).onChanged(Resource.loading(null))
        verify(service).fetchDiscoverMovie(1)
        verify(movieDao).insertMovieList(mockResponse.results)

        updatedData.postValue(mockResponse.results)
        verify(observer).onChanged(Resource.success(mockResponse.results, false))
    }

    @Test
    fun loadTvListFromNetwork() {
        val loadFromDb = MutableLiveData<List<Tv>>()
        whenever(tvDao.getTvList(1)).thenReturn(loadFromDb)

        val mockResponse = DiscoverTvResponse(1, emptyList(), 100, 10)
        val call = successCall(mockResponse)
        whenever(service.fetchDiscoverTv(1)).thenReturn(call)

        val data = repository.loadTvs(1)
        verify(tvDao).getTvList(1)
        verifyNoMoreInteractions(service)

        val observer = mock<Observer<Resource<List<Tv>>>>()
        data.observeForever(observer)
        verifyNoMoreInteractions(service)
        val updateData = MutableLiveData<List<Tv>>()
        whenever(tvDao.getTvList(1)).thenReturn(updateData)

        loadFromDb.postValue(null)
        verify(observer).onChanged(Resource.loading(null))
        verify(service).fetchDiscoverTv(1)
        verify(tvDao).insertTv(mockResponse.results)

        updateData.postValue(mockResponse.results)
        verify(observer).onChanged(Resource.success(mockResponse.results, false))

    }
}
