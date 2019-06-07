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
package com.skydoves.themovies.view.ui.details.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.skydoves.themovies.models.Keyword
import com.skydoves.themovies.models.Resource
import com.skydoves.themovies.models.Review
import com.skydoves.themovies.models.Video
import com.skydoves.themovies.repository.TvRepository
import com.skydoves.themovies.utils.AbsentLiveData
import timber.log.Timber
import javax.inject.Inject

class TvDetailViewModel @Inject
constructor(private val repository: TvRepository) : ViewModel() {

  private val keywordIdLiveData: MutableLiveData<Int> = MutableLiveData()
  private val keywordListLiveData: LiveData<Resource<List<Keyword>>>

  private val videoIdLiveData: MutableLiveData<Int> = MutableLiveData()
  private val videoListLiveData: LiveData<Resource<List<Video>>>

  private val reviewIdLiveData: MutableLiveData<Int> = MutableLiveData()
  private val reviewListLiveData: LiveData<Resource<List<Review>>>

  init {
    Timber.d("Injection TvDetailViewModel")

    keywordListLiveData = keywordIdLiveData.switchMap {
      keywordIdLiveData.value?.let { repository.loadKeywordList(it) }
        ?: AbsentLiveData.create()
    }

    videoListLiveData = videoIdLiveData.switchMap {
      videoIdLiveData.value?.let { repository.loadVideoList(it) } ?: AbsentLiveData.create()
    }

    reviewListLiveData = reviewIdLiveData.switchMap {
      reviewIdLiveData.value?.let { repository.loadReviewsList(it) }
        ?: AbsentLiveData.create()
    }
  }

  fun getKeywordListObservable() = keywordListLiveData
  fun postKeywordId(id: Int) = keywordIdLiveData.postValue(id)

  fun getVideoListObservable() = videoListLiveData
  fun postVideoId(id: Int) = videoIdLiveData.postValue(id)

  fun getReviewListObservable() = reviewListLiveData
  fun postReviewId(id: Int) = reviewIdLiveData.postValue(id)
}
