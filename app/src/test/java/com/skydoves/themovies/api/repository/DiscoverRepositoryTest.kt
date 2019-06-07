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
package com.skydoves.themovies.api.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.skydoves.themovies.api.TheDiscoverService
import com.skydoves.themovies.api.api.ApiUtil.successCall
import com.skydoves.themovies.models.Resource
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

@RunWith(JUnit4::class)
class DiscoverRepositoryTest {

  private lateinit var repository: DiscoverRepository
  private val movieDao = mock<MovieDao>()
  private val tvDao = mock<TvDao>()
  private val service = mock<TheDiscoverService>()

  @Rule
  @JvmField
  val instantExecutorRule = InstantTaskExecutorRule()

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
