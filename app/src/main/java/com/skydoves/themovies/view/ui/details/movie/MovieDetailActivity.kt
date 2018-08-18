package com.skydoves.themovies.view.ui.details.movie

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.skydoves.themovies.R
import com.skydoves.themovies.models.Keyword
import com.skydoves.themovies.models.Resource
import com.skydoves.themovies.models.Review
import com.skydoves.themovies.models.Video
import com.skydoves.themovies.models.entity.Movie
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.toolbar_default.*
import org.jetbrains.anko.toast
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
        toolbar_home.setOnClickListener { onBackPressed() }
        toolbar_title.text = getMovieFromIntent().title
    }

    private fun observeViewModel() {
        viewModel.getKeywordListObservable().observe(this, Observer { it?.let { updateKeywordList(it)} })
        viewModel.postKeywordId(getMovieFromIntent().id)

        viewModel.getVideoListObservable().observe(this, Observer { it?.let { updateVideoList(it) } })
        viewModel.postVideoId(getMovieFromIntent().id)

        viewModel.getReviewListObservable().observe(this, Observer { it?.let { updateReviewList(it) } })
        viewModel.postReviewId(getMovieFromIntent().id)
    }

    private fun updateKeywordList(resource: Resource<List<Keyword>>) {
        toast(resource.data.toString())
    }

    private fun updateVideoList(resource: Resource<List<Video>>) {
        toast(resource.data.toString())
    }

    private fun updateReviewList(resource: Resource<List<Review>>) {
        toast(resource.data.toString())
    }

    private fun getMovieFromIntent(): Movie {
        return intent.getParcelableExtra("movie") as Movie
    }
}
