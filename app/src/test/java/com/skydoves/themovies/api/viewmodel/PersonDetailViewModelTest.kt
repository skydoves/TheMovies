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
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.skydoves.themovies.api.PeopleService
import com.skydoves.themovies.api.api.ApiUtil
import com.skydoves.themovies.models.Resource
import com.skydoves.themovies.models.network.PersonDetail
import com.skydoves.themovies.repository.PeopleRepository
import com.skydoves.themovies.room.PeopleDao
import com.skydoves.themovies.utils.MockTestUtil
import com.skydoves.themovies.utils.MockTestUtil.Companion.mockPerson
import com.skydoves.themovies.utils.MockTestUtil.Companion.mockPersonDetail
import com.skydoves.themovies.view.ui.details.person.PersonDetailViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PersonDetailViewModelTest {

  private lateinit var viewModel: PersonDetailViewModel

  private lateinit var repository: PeopleRepository
  private val peopleDao = mock<PeopleDao>()
  private val service = mock<PeopleService>()

  @Rule
  @JvmField
  val instantExecutorRule = InstantTaskExecutorRule()

  @Before
  fun init() {
    repository = PeopleRepository(service, peopleDao)
    viewModel = PersonDetailViewModel(repository)
  }

  @Test
  fun loadPersonDetail() {
    val loadFromDB = mockPerson()
    whenever(peopleDao.getPerson(123)).thenReturn(loadFromDB)

    val mockResponse = mockPersonDetail()
    val call = ApiUtil.successCall(mockResponse)
    whenever(service.fetchPersonDetail(123)).thenReturn(call)

    val data = repository.loadPersonDetail(123)
    val observer = mock<Observer<Resource<PersonDetail>>>()
    data.observeForever(observer)

    viewModel.postPersonId(123)
    verify(peopleDao, times(3)).getPerson(123)
    verify(observer).onChanged(
        Resource.success(MockTestUtil.mockPersonDetail(), true))

    val updatedPerson = MockTestUtil.mockPerson()
    updatedPerson.personDetail = MockTestUtil.mockPersonDetail()
    verify(peopleDao).updatePerson(updatedPerson)
  }
}
