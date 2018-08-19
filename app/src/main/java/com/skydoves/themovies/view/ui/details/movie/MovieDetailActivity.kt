package com.skydoves.themovies.view.ui.details.movie

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.skydoves.themovies.R
import com.skydoves.themovies.api.Api
import com.skydoves.themovies.extension.applyToolbarMargin
import com.skydoves.themovies.extension.observeLiveData
import com.skydoves.themovies.extension.requestGlideListener
import com.skydoves.themovies.extension.simpleToolbarWithHome
import com.skydoves.themovies.models.Keyword
import com.skydoves.themovies.models.Resource
import com.skydoves.themovies.models.Review
import com.skydoves.themovies.models.Video
import com.skydoves.themovies.models.entity.Movie
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.layout_detail_header.*
import javax.inject.Inject

/**
 * Developed by skydoves on 2018-08-16.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class MovieDetailActivity : AppCompatActivity() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(MovieDetailViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        initializeUI()
        observeViewModel()
    }

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

    }

    private fun updateVideoList(resource: Resource<List<Video>>) {

    }

    private fun updateReviewList(resource: Resource<List<Review>>) {

    }

    private fun getMovieFromIntent(): Movie {
        return intent.getParcelableExtra("movie") as Movie
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == android.R.id.home) onBackPressed()
        return false
    }
}
