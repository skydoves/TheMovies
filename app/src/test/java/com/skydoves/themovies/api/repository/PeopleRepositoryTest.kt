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
import com.skydoves.themovies.api.PeopleService
import com.skydoves.themovies.api.api.ApiUtil.successCall
import com.skydoves.themovies.models.Resource
import com.skydoves.themovies.models.entity.Person
import com.skydoves.themovies.models.network.PeopleResponse
import com.skydoves.themovies.models.network.PersonDetail
import com.skydoves.themovies.repository.PeopleRepository
import com.skydoves.themovies.room.PeopleDao
import com.skydoves.themovies.utils.MockTestUtil.Companion.mockPerson
import com.skydoves.themovies.utils.MockTestUtil.Companion.mockPersonDetail
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PeopleRepositoryTest {

  private lateinit var repository: PeopleRepository
  private val peopleDao = mock<PeopleDao>()
  private val service = mock<PeopleService>()

  @Rule
  @JvmField
  val instantExecutorRule = InstantTaskExecutorRule()

  @Before
  fun init() {
    repository = PeopleRepository(service, peopleDao)
  }

  @Test
  fun loadPeopleFromNetwork() {
    val loadFromDB = MutableLiveData<List<Person>>()
    whenever(peopleDao.getPeople(1)).thenReturn(loadFromDB)

    val mockResponse = PeopleResponse(1, emptyList(), 100, 10)
    val call = successCall(mockResponse)
    whenever(service.fetchPopularPeople(1)).thenReturn(call)

    val data = repository.loadPeople(1)
    verify(peopleDao).getPeople(1)
    verifyNoMoreInteractions(service)

    val observer = mock<Observer<Resource<List<Person>>>>()
    data.observeForever(observer)
    verifyNoMoreInteractions(service)
    val updatedData = MutableLiveData<List<Person>>()
    whenever(peopleDao.getPeople(1)).thenReturn(updatedData)

    loadFromDB.postValue(null)
    verify(observer).onChanged(Resource.loading(null))
    verify(service).fetchPopularPeople(1)
    verify(peopleDao).insertPeople(mockResponse.results)

    updatedData.postValue(mockResponse.results)
    verify(observer).onChanged(Resource.success(mockResponse.results, false))
  }

  @Test
  fun loadPersonDetailFromNetwork() {
    val loadFromDB = mockPerson()
    whenever(peopleDao.getPerson(123)).thenReturn(loadFromDB)

    val mockResponse = mockPersonDetail()
    val call = successCall(mockResponse)
    whenever(service.fetchPersonDetail(123)).thenReturn(call)

    val data = repository.loadPersonDetail(123)
    verify(peopleDao).getPerson(123)
    verifyNoMoreInteractions(service)

    val observer = mock<Observer<Resource<PersonDetail>>>()
    data.observeForever(observer)
    verify(observer).onChanged(Resource.success(mockPersonDetail(), true))

    val updatedPerson = mockPerson()
    updatedPerson.personDetail = mockPersonDetail()
    verify(peopleDao).updatePerson(updatedPerson)
  }
}
