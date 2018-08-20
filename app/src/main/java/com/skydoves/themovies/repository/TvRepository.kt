package com.skydoves.themovies.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.skydoves.themovies.api.ApiResponse
import com.skydoves.themovies.api.TvService
import com.skydoves.themovies.mappers.KeywordResponseMapper
import com.skydoves.themovies.mappers.ReviewResponseMapper
import com.skydoves.themovies.mappers.VideoResponseMapper
import com.skydoves.themovies.models.Keyword
import com.skydoves.themovies.models.Resource
import com.skydoves.themovies.models.Review
import com.skydoves.themovies.models.Video
import com.skydoves.themovies.models.network.KeywordListResponse
import com.skydoves.themovies.models.network.ReviewListResponse
import com.skydoves.themovies.models.network.VideoListResponse
import com.skydoves.themovies.room.TvDao
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Developed by skydoves on 2018-08-20.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@Singleton
class TvRepository @Inject
constructor(val service: TvService, val tvDao: TvDao): Repository {

    init {
        Timber.d("Injection TvRepository")
    }

    fun loadKeywordList(id: Int): LiveData<Resource<List<Keyword>>> {
        return object: NetworkBoundRepository<List<Keyword>, KeywordListResponse, KeywordResponseMapper>() {
            override fun saveFetchData(items: KeywordListResponse) {
                val tv = tvDao.getTv(id_ = id)
                tv.keywords = items.keywords
                tvDao.updateTv(tv = tv)
            }

            override fun shouldFetch(data: List<Keyword>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<Keyword>> {
                val movie = tvDao.getTv(id_ = id)
                val data: MutableLiveData<List<Keyword>> = MutableLiveData()
                data.value = movie.keywords
                return data
            }

            override fun fetchService(): LiveData<ApiResponse<KeywordListResponse>> {
                return service.fetchKeywords(id = id)
            }

            override fun mapper(): KeywordResponseMapper {
                return KeywordResponseMapper()
            }

            override fun onFetchFailed(message: String?) {
                Timber.d("onFetchFailed : $message")
            }
        }.asLiveData()
    }

    fun loadVideoList(id: Int): LiveData<Resource<List<Video>>> {
        return object : NetworkBoundRepository<List<Video>, VideoListResponse, VideoResponseMapper>() {
            override fun saveFetchData(items: VideoListResponse) {
                val tv = tvDao.getTv(id_ = id)
                tv.videos = items.results
                tvDao.updateTv(tv = tv)
            }

            override fun shouldFetch(data: List<Video>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<Video>> {
                val movie = tvDao.getTv(id_ = id)
                val data: MutableLiveData<List<Video>> = MutableLiveData()
                data.value = movie.videos
                return data
            }

            override fun fetchService(): LiveData<ApiResponse<VideoListResponse>> {
                return service.fetchVideos(id = id)
            }

            override fun mapper(): VideoResponseMapper {
                return VideoResponseMapper()
            }

            override fun onFetchFailed(message: String?) {
                Timber.d("onFetchFailed : $message")
            }
        }.asLiveData()
    }

    fun loadReviewsList(id: Int): LiveData<Resource<List<Review>>> {
        return object: NetworkBoundRepository<List<Review>, ReviewListResponse, ReviewResponseMapper>() {
            override fun saveFetchData(items: ReviewListResponse) {
                val tv = tvDao.getTv(id_ = id)
                tv.reviews = items.results
                tvDao.updateTv(tv = tv)
            }

            override fun shouldFetch(data: List<Review>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<Review>> {
                val movie = tvDao.getTv(id_ = id)
                val data: MutableLiveData<List<Review>> = MutableLiveData()
                data.value = movie.reviews
                return data
            }

            override fun fetchService(): LiveData<ApiResponse<ReviewListResponse>> {
                return service.fetchReviews(id = id)
            }

            override fun mapper(): ReviewResponseMapper {
                return ReviewResponseMapper()
            }

            override fun onFetchFailed(message: String?) {
                Timber.d("onFetchFailed : $message")
            }
        }.asLiveData()
    }
}
