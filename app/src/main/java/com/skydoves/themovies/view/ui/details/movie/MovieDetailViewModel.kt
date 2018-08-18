package com.skydoves.themovies.view.ui.details.movie

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.skydoves.themovies.models.Keyword
import com.skydoves.themovies.models.Resource
import com.skydoves.themovies.models.Video
import com.skydoves.themovies.repository.MovieRepository
import com.skydoves.themovies.utils.AbsentLiveData
import timber.log.Timber
import javax.inject.Inject

/**
 * Developed by skydoves on 2018-08-18.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class MovieDetailViewModel @Inject
constructor(val repository: MovieRepository): ViewModel() {

    private val keywordPageLiveData: MutableLiveData<Int> = MutableLiveData()
    private val keywordListLiveData: LiveData<Resource<List<Keyword>>>

    private val videoPageLiveData: MutableLiveData<Int> = MutableLiveData()
    private val videoListLiveData: LiveData<Resource<List<Video>>>

    init {
        Timber.d("Injection MovieDetailViewModel")

        keywordListLiveData = Transformations.switchMap(keywordPageLiveData) {
            keywordPageLiveData.value?.let { repository.loadKeywordList(it) } ?:
                    AbsentLiveData.create()
        }

        videoListLiveData = Transformations.switchMap(videoPageLiveData) {
            videoPageLiveData.value?.let { repository.loadVideoList(it) } ?:
                    AbsentLiveData.create()
        }
    }

    fun getKeywordListObservable() = keywordListLiveData
    fun getKeywordListValues() = getKeywordListObservable().value
    fun postKeywordPage(page: Int) = keywordPageLiveData.postValue(page)

    fun getVideoListObservable() = videoListLiveData
    fun getVideoListValues() = getVideoListObservable().value
    fun postVideoPage(page: Int) = videoPageLiveData.postValue(page)
}
