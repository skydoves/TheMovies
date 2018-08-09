package com.skydoves.themovies.view.ui.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.skydoves.themovies.models.Movie
import com.skydoves.themovies.models.Resource
import com.skydoves.themovies.repository.DiscoverRepository
import timber.log.Timber
import javax.inject.Inject

/**
 * Developed by skydoves on 2018-08-07.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class MainActivityViewModel @Inject
constructor(): ViewModel() {

    var posters: MutableLiveData<Resource<List<Movie>>> = MutableLiveData()

    init {
        Timber.d("injection MainActivityViewModel")
    }

    fun fetchDiscovers() {
/*        Transformations.map(discoverRepository.loadMovies(0)) {
            posters.value = it
        }*/
    }
}
