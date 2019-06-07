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
package com.skydoves.themovies.api.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
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
import com.skydoves.themovies.utils.MockTestUtil.Companion.mockKeywordList
import com.skydoves.themovies.utils.MockTestUtil.Companion.mockTv
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TvRepositoryTest {

  private lateinit var repository: TvRepository
  private val tvDao = mock<TvDao>()
  private val service = mock<TvService>()

  @Rule
  @JvmField
  val instantExecutorRule = InstantTaskExecutorRule()

  @Before
  fun init() {
    repository = TvRepository(service, tvDao)
  }

  @Test
  fun loadKeywordListFromNetwork() {
    val loadFromDB = mockTv()
    whenever(tvDao.getTv(123)).thenReturn(loadFromDB)

    val mockResponse = KeywordListResponse(123, MockTestUtil.mockKeywordList())
    val call = ApiUtil.successCall(mockResponse)
    whenever(service.fetchKeywords(123)).thenReturn(call)

    val data = repository.loadKeywordList(123)
    verify(tvDao).getTv(123)
    verifyNoMoreInteractions(service)

    val observer = mock<Observer<Resource<List<Keyword>>>>()
    data.observeForever(observer)
    verify(observer).onChanged(Resource.success(MockTestUtil.mockKeywordList(), true))

    val updatedTv = mockTv()
    updatedTv.keywords = mockKeywordList()
    verify(tvDao).updateTv(updatedTv)
  }

  @Test
  fun loadVideoListFromNetwork() {
    val loadFromDB = mockTv()
    whenever(tvDao.getTv(123)).thenReturn(loadFromDB)

    val mockResponse = VideoListResponse(123, MockTestUtil.mockVideoList())
    val call = ApiUtil.successCall(mockResponse)
    whenever(service.fetchVideos(123)).thenReturn(call)

    val data = repository.loadVideoList(123)
    verify(tvDao).getTv(123)
    verifyNoMoreInteractions(service)

    val observer = mock<Observer<Resource<List<Video>>>>()
    data.observeForever(observer)
    verify(observer).onChanged(Resource.success(MockTestUtil.mockVideoList(), false))

    val updatedTv = mockTv()
    updatedTv.videos = MockTestUtil.mockVideoList()
    verify(tvDao).updateTv(updatedTv)
  }

  @Test
  fun loadReviewListFromNetwork() {
    val loadFromDB = mockTv()
    whenever(tvDao.getTv(123)).thenReturn(loadFromDB)

    val mockResponse = ReviewListResponse(123, 1, MockTestUtil.mockReviewList(), 100, 100)
    val call = ApiUtil.successCall(mockResponse)
    whenever(service.fetchReviews(123)).thenReturn(call)

    val data = repository.loadReviewsList(123)
    verify(tvDao).getTv(123)
    verifyNoMoreInteractions(service)

    val observer = mock<Observer<Resource<List<Review>>>>()
    data.observeForever(observer)
    verify(observer).onChanged(Resource.success(MockTestUtil.mockReviewList(), false))

    val updatedTv = mockTv()
    updatedTv.reviews = MockTestUtil.mockReviewList()
    verify(tvDao).updateTv(updatedTv)
  }
}
