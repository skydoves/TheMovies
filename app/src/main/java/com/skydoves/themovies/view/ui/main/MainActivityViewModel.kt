package com.skydoves.themovies.view.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.skydoves.themovies.models.Movie
import com.skydoves.themovies.models.Resource
import com.skydoves.themovies.repository.DiscoverRepository
import com.skydoves.themovies.utils.AbsentLiveData
import timber.log.Timber
import javax.inject.Inject

/**
 * Developed by skydoves on 2018-08-07.®
 * Copyright (c) 2018 skydoves rights reserved.
 */

class MainActivityViewModel @Inject
constructor(private val repository: DiscoverRepository): ViewModel() {

    var pageLiveData: MutableLiveData<Int> = MutableLiveData()
    val movieListLiveData: LiveData<Resource<List<Movie>>>

    init {
        Timber.d("injection MainActivityViewModel")

        movieListLiveData = Transformations.switchMap(pageLiveData) {
            pageLiveData.value?.let { repository.loadMovies(it) } ?:
                    AbsentLiveData.create()
        }
    }
}
