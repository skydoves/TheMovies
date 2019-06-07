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
package com.skydoves.themovies.view.ui.details.movie

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.skydoves.themovies.R
import com.skydoves.themovies.api.Api
import com.skydoves.themovies.extension.applyToolbarMargin
import com.skydoves.themovies.extension.observeLiveData
import com.skydoves.themovies.extension.requestGlideListener
import com.skydoves.themovies.extension.simpleToolbarWithHome
import com.skydoves.themovies.extension.visible
import com.skydoves.themovies.extension.vm
import com.skydoves.themovies.models.Keyword
import com.skydoves.themovies.models.Resource
import com.skydoves.themovies.models.Review
import com.skydoves.themovies.models.Status
import com.skydoves.themovies.models.Video
import com.skydoves.themovies.models.entity.Movie
import com.skydoves.themovies.utils.KeywordListMapper
import com.skydoves.themovies.view.adapter.ReviewListAdapter
import com.skydoves.themovies.view.adapter.VideoListAdapter
import com.skydoves.themovies.view.viewholder.VideoListViewHolder
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.layout_detail_body.*
import kotlinx.android.synthetic.main.layout_detail_header.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * Developed by skydoves on 2018-08-16.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class MovieDetailActivity : AppCompatActivity(), VideoListViewHolder.Delegate {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory
  private val viewModel by lazy { vm(viewModelFactory, MovieDetailViewModel::class) }

  private val videoAdapter by lazy { VideoListAdapter(this) }
  private val reviewAdapter by lazy { ReviewListAdapter() }

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_movie_detail)

    initializeUI()
    observeViewModel()
  }

  @SuppressLint("SetTextI18n")
  private fun initializeUI() {
    applyToolbarMargin(movie_detail_toolbar)
    simpleToolbarWithHome(movie_detail_toolbar, getMovieFromIntent().title)
    getMovieFromIntent().backdrop_path?.let {
      Glide.with(this).load(Api.getBackdropPath(it))
        .listener(requestGlideListener(movie_detail_poster))
        .into(movie_detail_poster)
    } ?: let {
      Glide.with(this).load(Api.getBackdropPath(getMovieFromIntent().poster_path!!))
        .listener(requestGlideListener(movie_detail_poster))
        .into(movie_detail_poster)
    }
    detail_header_title.text = getMovieFromIntent().title
    detail_header_release.text = "Release Date : ${getMovieFromIntent().release_date}"
    detail_header_star.rating = getMovieFromIntent().vote_average / 2
    detail_body_recyclerView_trailers.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    detail_body_recyclerView_trailers.adapter = videoAdapter
    detail_body_summary.text = getMovieFromIntent().overview
    detail_body_recyclerView_reviews.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    detail_body_recyclerView_reviews.adapter = reviewAdapter
    detail_body_recyclerView_reviews.isNestedScrollingEnabled = false
    detail_body_recyclerView_reviews.setHasFixedSize(true)
  }

  private fun observeViewModel() {
    observeLiveData(viewModel.getKeywordListObservable()) { updateKeywordList(it) }
    viewModel.postKeywordId(getMovieFromIntent().id)

    observeLiveData(viewModel.getVideoListObservable()) { updateVideoList(it) }
    viewModel.postVideoId(getMovieFromIntent().id)

    observeLiveData(viewModel.getReviewListObservable()) { updateReviewList(it) }
    viewModel.postReviewId(getMovieFromIntent().id)
  }

  private fun updateKeywordList(resource: Resource<List<Keyword>>) {
    when (resource.status) {
      Status.SUCCESS -> {
        detail_body_tags.tags = KeywordListMapper.mapToStringList(resource.data!!)

        if (resource.data.isNotEmpty()) {
          detail_body_tags.visible()
        }
      }
      Status.ERROR -> toast(resource.errorEnvelope?.status_message.toString())
      Status.LOADING -> Unit
    }
  }

  private fun updateVideoList(resource: Resource<List<Video>>) {
    when (resource.status) {
      Status.SUCCESS -> {
        videoAdapter.addVideoList(resource)

        if (resource.data?.isNotEmpty()!!) {
          detail_body_trailers.visible()
          detail_body_recyclerView_trailers.visible()
        }
      }
      Status.ERROR -> toast(resource.errorEnvelope?.status_message.toString())
      Status.LOADING -> Unit
    }
  }

  private fun updateReviewList(resource: Resource<List<Review>>) {
    when (resource.status) {
      Status.LOADING -> Unit
      Status.SUCCESS -> {
        reviewAdapter.addReviewList(resource)
        if (resource.data?.isNotEmpty()!!) {
          detail_body_reviews.visible()
          detail_body_recyclerView_reviews.visible()
        }
      }
      Status.ERROR -> toast(resource.errorEnvelope?.status_message.toString())
    }
  }

  private fun getMovieFromIntent(): Movie {
    return intent.getParcelableExtra(movieId) as Movie
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    if (item?.itemId == android.R.id.home) onBackPressed()
    return false
  }

  override fun onItemClicked(video: Video) {
    val playVideoIntent = Intent(Intent.ACTION_VIEW, Uri.parse(Api.getYoutubeVideoPath(video.key)))
    startActivity(playVideoIntent)
  }

  companion object {
    private const val movieId = "movie"
    fun startActivityModel(context: Context?, movie: Movie) {
      context?.startActivity<MovieDetailActivity>(movieId to movie)
    }
  }
}
