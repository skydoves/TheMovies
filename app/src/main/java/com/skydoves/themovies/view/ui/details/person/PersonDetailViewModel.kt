package com.skydoves.themovies.view.ui.details.person

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.skydoves.themovies.models.Resource
import com.skydoves.themovies.models.network.PersonDetail
import com.skydoves.themovies.repository.PeopleRepository
import com.skydoves.themovies.utils.AbsentLiveData
import timber.log.Timber
import javax.inject.Inject

/**
 * Developed by skydoves on 2018-08-22.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class PersonDetailViewModel @Inject
constructor(private val repository: PeopleRepository) : ViewModel() {

  private val personIdLiveData: MutableLiveData<Int> = MutableLiveData()
  private val personLiveData: LiveData<Resource<PersonDetail>>

  init {
    Timber.d("Injection : PersonDetailViewModel")

    personLiveData = personIdLiveData.switchMap {
      personIdLiveData.value?.let { repository.loadPersonDetail(it) }
          ?: AbsentLiveData.create()
    }
  }

  fun getPersonObservable() = personLiveData
  fun postPersonId(id: Int) = personIdLiveData.postValue(id)
}
