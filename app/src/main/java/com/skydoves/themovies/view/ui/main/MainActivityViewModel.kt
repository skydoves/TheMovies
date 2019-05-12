package com.skydoves.themovies.view.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.skydoves.themovies.models.Resource
import com.skydoves.themovies.models.entity.Movie
import com.skydoves.themovies.models.entity.Person
import com.skydoves.themovies.models.entity.Tv
import com.skydoves.themovies.repository.DiscoverRepository
import com.skydoves.themovies.repository.PeopleRepository
import com.skydoves.themovies.utils.AbsentLiveData
import timber.log.Timber
import javax.inject.Inject

/**
 * Developed by skydoves on 2018-08-07.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class MainActivityViewModel @Inject
constructor(
  private val discoverRepository: DiscoverRepository,
  private val peopleRepository: PeopleRepository
)
  : ViewModel() {

  private var moviePageLiveData: MutableLiveData<Int> = MutableLiveData()
  private val movieListLiveData: LiveData<Resource<List<Movie>>>

  private var tvPageLiveData: MutableLiveData<Int> = MutableLiveData()
  private val tvListLiveData: LiveData<Resource<List<Tv>>>

  private var peoplePageLiveData: MutableLiveData<Int> = MutableLiveData()
  private val peopleLiveData: LiveData<Resource<List<Person>>>

  init {
    Timber.d("injection MainActivityViewModel")

    movieListLiveData = moviePageLiveData.switchMap {
      moviePageLiveData.value?.let { discoverRepository.loadMovies(it) }
          ?: AbsentLiveData.create()
    }

    tvListLiveData = tvPageLiveData.switchMap {
      tvPageLiveData.value?.let { discoverRepository.loadTvs(it) } ?: AbsentLiveData.create()
    }

    peopleLiveData = peoplePageLiveData.switchMap {
      peoplePageLiveData.value?.let { peopleRepository.loadPeople(it) }
          ?: AbsentLiveData.create()
    }
  }

  fun getMovieListObservable() = movieListLiveData
  fun getMovieListValues() = getMovieListObservable().value
  fun postMoviePage(page: Int) = moviePageLiveData.postValue(page)

  fun getTvListObservable() = tvListLiveData
  fun getTvListValues() = getTvListObservable().value
  fun postTvPage(page: Int) = tvPageLiveData.postValue(page)

  fun getPeopleObservable() = peopleLiveData
  fun getPeopleValues() = getPeopleObservable().value
  fun postPeoplePage(page: Int) = peoplePageLiveData.postValue(page)
}
