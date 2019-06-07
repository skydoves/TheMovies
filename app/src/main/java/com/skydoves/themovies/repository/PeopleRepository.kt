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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.skydoves.themovies.api.ApiResponse
import com.skydoves.themovies.api.PeopleService
import com.skydoves.themovies.mappers.PeopleResponseMapper
import com.skydoves.themovies.mappers.PersonDetailResponseMapper
import com.skydoves.themovies.models.Resource
import com.skydoves.themovies.models.entity.Person
import com.skydoves.themovies.models.network.PeopleResponse
import com.skydoves.themovies.models.network.PersonDetail
import com.skydoves.themovies.room.PeopleDao
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PeopleRepository @Inject
constructor(val peopleService: PeopleService, val peopleDao: PeopleDao)
  : Repository {

  init {
    Timber.d("Injection PeopleRepository")
  }

  fun loadPeople(page: Int): LiveData<Resource<List<Person>>> {
    return object : NetworkBoundRepository<List<Person>, PeopleResponse, PeopleResponseMapper>() {
      override fun saveFetchData(items: PeopleResponse) {
        for (item in items.results) {
          item.page = page
        }
        peopleDao.insertPeople(items.results)
      }

      override fun shouldFetch(data: List<Person>?): Boolean {
        return data == null || data.isEmpty()
      }

      override fun loadFromDb(): LiveData<List<Person>> {
        return peopleDao.getPeople(page_ = page)
      }

      override fun fetchService(): LiveData<ApiResponse<PeopleResponse>> {
        return peopleService.fetchPopularPeople(page = page)
      }

      override fun mapper(): PeopleResponseMapper {
        return PeopleResponseMapper()
      }

      override fun onFetchFailed(message: String?) {
        Timber.d("onFetchFailed : $message")
      }
    }.asLiveData()
  }

  fun loadPersonDetail(id: Int): LiveData<Resource<PersonDetail>> {
    return object : NetworkBoundRepository<PersonDetail, PersonDetail, PersonDetailResponseMapper>() {
      override fun saveFetchData(items: PersonDetail) {
        val person = peopleDao.getPerson(id_ = id)
        person.personDetail = items
        peopleDao.updatePerson(person = person)
      }

      override fun shouldFetch(data: PersonDetail?): Boolean {
        return data == null || data.biography.isEmpty()
      }

      override fun loadFromDb(): LiveData<PersonDetail> {
        val person = peopleDao.getPerson(id_ = id)
        val data: MutableLiveData<PersonDetail> = MutableLiveData()
        data.value = person.personDetail
        return data
      }

      override fun fetchService(): LiveData<ApiResponse<PersonDetail>> {
        return peopleService.fetchPersonDetail(id = id)
      }

      override fun mapper(): PersonDetailResponseMapper {
        return PersonDetailResponseMapper()
      }

      override fun onFetchFailed(message: String?) {
        Timber.d("onFetchFailed : $message")
      }
    }.asLiveData()
  }
}
