package com.skydoves.themovies.view.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.skydoves.themovies.R
import com.skydoves.themovies.factory.AppViewModelFactory
import com.skydoves.themovies.models.Movie
import com.skydoves.themovies.models.Resource
import com.skydoves.themovies.models.Status
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

/**
 * Developed by skydoves on 2018-08-07.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class MainActivity : AppCompatActivity() {

    @Inject lateinit var viewModelFactory: AppViewModelFactory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeUI()
        observeViewModels()
    }

    private fun initializeUI() {
        viewModel.moviePageLiveData.value = 1
    }

    private fun observeViewModels() {
        viewModel.movieListLiveData.observe(this, Observer { it?.let { updateMovieList(it) }})
    }

    private fun updateMovieList(resource: Resource<List<Movie>>) {
        when(resource.status) {
            Status.SUCCESS -> { textView.text = resource.data.toString() }
            Status.ERROR -> { textView.text = resource.errorEnvelope?.status_message }
            Status.LOADING -> { }
        }
    }
}
