package com.skydoves.themovies.api.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.skydoves.themovies.api.TvService
import com.skydoves.themovies.api.api.ApiUtil
import com.skydoves.themovies.models.Keyword
import com.skydoves.themovies.models.Resource
import com.skydoves.themovies.models.Review
import com.skydoves.themovies.models.Video
import com.skydoves.themovies.models.network.KeywordListResponse
import com.skydoves.themovies.models.network.ReviewListResponse
import com.skydoves.themovies.models.network.VideoListResponse
import com.skydoves.themovies.repository.TvRepository
import com.skydoves.themovies.room.TvDao
import com.skydoves.themovies.utils.MockTestUtil
import com.skydoves.themovies.utils.MockTestUtil.Companion.mockTv
import com.skydoves.themovies.view.ui.details.tv.TvDetailViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Developed by skydoves on 2018-08-21.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@RunWith(JUnit4::class)
class TvDetailViewModelTest {

    private lateinit var viewModel: TvDetailViewModel

    private lateinit var repository: TvRepository
    private val tvDao = mock<TvDao>()

    private val service = mock<TvService>()

    @Rule
    @JvmField val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        repository = TvRepository(service, tvDao)
        viewModel = TvDetailViewModel(repository)
    }

    @Test
    fun loadKeywordList() {
        val loadFromDB = mockTv()
        whenever(tvDao.getTv(123)).thenReturn(loadFromDB)

        val mockResponse = KeywordListResponse(123, MockTestUtil.mockKeywordList())
        val call = ApiUtil.successCall(mockResponse)
        whenever(service.fetchKeywords(123)).thenReturn(call)

        val data = repository.loadKeywordList(123)
        val observer = mock<Observer<Resource<List<Keyword>>>>()
        data.observeForever(observer)

        viewModel.postKeywordId(123)
        verify(tvDao, times(3)).getTv(123)
        verify(observer).onChanged(
                Resource.success(MockTestUtil.mockKeywordList(), true))

        val updatedTv = mockTv()
        updatedTv.keywords = MockTestUtil.mockKeywordList()
        verify(tvDao).updateTv(updatedTv)
    }

    @Test
    fun loadVideoList() {
        val loadFromDB = mockTv()
        whenever(tvDao.getTv(123)).thenReturn(loadFromDB)

        val mockResponse = VideoListResponse(123, MockTestUtil.mockVideoList())
        val call = ApiUtil.successCall(mockResponse)
        whenever(service.fetchVideos(123)).thenReturn(call)

        val data = repository.loadVideoList(123)
        val observer = mock<Observer<Resource<List<Video>>>>()
        data.observeForever(observer)

        viewModel.postVideoId(123)
        verify(tvDao, times(3)).getTv(123)
        verify(observer).onChanged(
                Resource.success(MockTestUtil.mockVideoList(), true)
        )

        val updatedTv = mockTv()
        updatedTv.videos = MockTestUtil.mockVideoList()
        verify(tvDao).updateTv(updatedTv)
    }

    @Test
    fun loadReviewList() {
        val loadFromDB = mockTv()
        whenever(tvDao.getTv(123)).thenReturn(loadFromDB)

        val mockResponse = ReviewListResponse(123, 1, MockTestUtil.mockReviewList(), 100, 100)
        val call = ApiUtil.successCall(mockResponse)
        whenever(service.fetchReviews(123)).thenReturn(call)

        val data = repository.loadReviewsList(123)
        val observer = mock<Observer<Resource<List<Review>>>>()
        data.observeForever(observer)

        viewModel.postReviewId(123)
        verify(tvDao, times(3)).getTv(123)
        verify(observer).onChanged(
                Resource.success(MockTestUtil.mockReviewList(), true)
        )

        val updatedTv = mockTv()
        updatedTv.reviews = MockTestUtil.mockReviewList()
        verify(tvDao).updateTv(updatedTv)
    }
}
