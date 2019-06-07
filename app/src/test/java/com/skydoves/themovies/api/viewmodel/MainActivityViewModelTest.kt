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
package com.skydoves.themovies.api.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.skydoves.themovies.api.PeopleService
import com.skydoves.themovies.api.TheDiscoverService
import com.skydoves.themovies.api.api.ApiUtil
import com.skydoves.themovies.models.Resource
import com.skydoves.themovies.models.entity.Movie
import com.skydoves.themovies.models.entity.Person
import com.skydoves.themovies.models.entity.Tv
import com.skydoves.themovies.models.network.DiscoverMovieResponse
import com.skydoves.themovies.repository.DiscoverRepository
import com.skydoves.themovies.repository.PeopleRepository
import com.skydoves.themovies.room.MovieDao
import com.skydoves.themovies.room.PeopleDao
import com.skydoves.themovies.room.TvDao
import com.skydoves.themovies.utils.MockTestUtil.Companion.mockMovie
import com.skydoves.themovies.utils.MockTestUtil.Companion.mockPerson
import com.skydoves.themovies.utils.MockTestUtil.Companion.mockTv
import com.skydoves.themovies.view.ui.main.MainActivityViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MainActivityViewModelTest {

  private lateinit var viewModel: MainActivityViewModel

  private lateinit var discoverRepository: DiscoverRepository
  private lateinit var peopleRepository: PeopleRepository

  private val movieDao = mock<MovieDao>()
  private val tvDao = mock<TvDao>()
  private val peopleDao = mock<PeopleDao>()

  private val discoverService = mock<TheDiscoverService>()
  private val peopleService = mock<PeopleService>()

  @Rule
  @JvmField
  val instantExecutorRule = InstantTaskExecutorRule()

  @Before
  fun init() {
    discoverRepository = DiscoverRepository(discoverService, movieDao, tvDao)
    peopleRepository = PeopleRepository(peopleService, peopleDao)
    viewModel = MainActivityViewModel(discoverRepository, peopleRepository)
  }

  @Test
  fun loadMovieList() {
    val loadFromDB = MutableLiveData<List<Movie>>()
    whenever(movieDao.getMovieList(1)).thenReturn(loadFromDB)

    val mockResponse = DiscoverMovieResponse(1, emptyList(), 100, 10)
    val call = ApiUtil.successCall(mockResponse)
    whenever(discoverService.fetchDiscoverMovie(1)).thenReturn(call)

    val data = viewModel.movieListLiveData
    val observer = mock<Observer<Resource<List<Movie>>>>()
    data.observeForever(observer)

    viewModel.postMoviePage(1)
    verify(movieDao).getMovieList(1)
    verifyNoMoreInteractions(discoverService)

    val mockMovieList = ArrayList<Movie>()
    mockMovieList.add(mockMovie())
    loadFromDB.postValue(mockMovieList)
    verify(observer).onChanged(
        Resource.success(viewModel.getMovieListValues()!!.data, false)
    )
  }

  @Test
  fun loadTvList() {
    val loadFromDB = MutableLiveData<List<Tv>>()
    whenever(tvDao.getTvList(1)).thenReturn(loadFromDB)

    val data = viewModel.tvListLiveData
    val observer = mock<Observer<Resource<List<Tv>>>>()
    data.observeForever(observer)

    viewModel.postTvPage(1)
    verify(tvDao).getTvList(1)
    verifyNoMoreInteractions(discoverService)

    val mockTvList = ArrayList<Tv>()
    mockTvList.add(mockTv())
    loadFromDB.postValue(mockTvList)
    verify(observer).onChanged(
        Resource.success(viewModel.getTvListValues()!!.data, false)
    )
  }

  @Test
  fun loadPeople() {
    val loadFromDB = MutableLiveData<List<Person>>()
    whenever(peopleDao.getPeople(1)).thenReturn(loadFromDB)

    val data = viewModel.peopleLiveData
    val observer = mock<Observer<Resource<List<Person>>>>()
    data.observeForever(observer)

    viewModel.postPeoplePage(1)
    verify(peopleDao).getPeople(1)
    verifyNoMoreInteractions(peopleService)

    val mockPeople = ArrayList<Person>()
    mockPeople.add(mockPerson())
    loadFromDB.postValue(mockPeople)
    verify(observer).onChanged(
        Resource.success(viewModel.getPeopleValues()!!.data, false)
    )
  }
}
