package com.skydoves.themovies.view.ui.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.skydoves.themovies.BuildConfig
import com.skydoves.themovies.api.TheDiscoverService
import com.skydoves.themovies.models.DiscoverResponse
import timber.log.Timber
import javax.inject.Inject

/**
 * Developed by skydoves on 2018-08-07.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class MainActivityViewModel @Inject
constructor(private val theDiscoverService: TheDiscoverService): ViewModel() {

    var posters: MutableLiveData<DiscoverResponse> = MutableLiveData()

    init {
        Timber.d("injection MainActivityViewModel")
    }

    fun fetchDiscovers() {
        theDiscoverService.fetchDiscoverTv(BuildConfig.TMDB_API_KEY).observeForever {
            posters.value = it?.body
        }
    }
}
