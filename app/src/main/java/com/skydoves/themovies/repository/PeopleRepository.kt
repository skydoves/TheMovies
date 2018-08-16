package com.skydoves.themovies.repository

import android.arch.lifecycle.LiveData
import com.skydoves.themovies.api.ApiResponse
import com.skydoves.themovies.api.PeopleService
import com.skydoves.themovies.mappers.PeopleResponseMapper
import com.skydoves.themovies.models.PeopleResponse
import com.skydoves.themovies.models.entity.Person
import com.skydoves.themovies.models.Resource
import com.skydoves.themovies.room.PeopleDao
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Developed by skydoves on 2018-08-15.
 * Copyright (c) 2018 skydoves rights reserved.
 */

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
                for(item in items.results) {
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
}
