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
package com.skydoves.themovies.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

@Singleton
class TvRepository @Inject
constructor(val service: TvService, val tvDao: TvDao)
  : Repository {

  init {
    Timber.d("Injection TvRepository")
  }

  fun loadKeywordList(id: Int): LiveData<Resource<List<Keyword>>> {
    return object : NetworkBoundRepository<List<Keyword>, KeywordListResponse, KeywordResponseMapper>() {
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
    return object : NetworkBoundRepository<List<Review>, ReviewListResponse, ReviewResponseMapper>() {
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
