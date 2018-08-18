package com.skydoves.themovies.view.ui.details.person

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.skydoves.themovies.R
import com.skydoves.themovies.models.entity.Person
import kotlinx.android.synthetic.main.toolbar_default.*
import javax.inject.Inject

/**
 * Developed by skydoves on 2018-08-16.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class PersonDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_detail)

        initializeUI()
    }

    private fun initializeUI() {
        toolbar_home.setOnClickListener { onBackPressed() }
        toolbar_title.text = getMovieFromIntent().name
    }

    private fun getMovieFromIntent(): Person {
        return intent.getParcelableExtra("person") as Person
    }
}
