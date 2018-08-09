package com.skydoves.themovies.view.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.skydoves.themovies.R
import com.skydoves.themovies.factory.AppViewModelFactory
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

        viewModel.posters.observe(this, Observer {
            textView.text = it.toString()
        })
        viewModel.pageLiveData.value = 1
    }
}
