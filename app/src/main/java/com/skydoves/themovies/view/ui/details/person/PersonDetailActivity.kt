package com.skydoves.themovies.view.ui.details.person

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.skydoves.themovies.R
import com.skydoves.themovies.api.Api
import com.skydoves.themovies.extension.observeLiveData
import com.skydoves.themovies.extension.visible
import com.skydoves.themovies.models.Resource
import com.skydoves.themovies.models.Status
import com.skydoves.themovies.models.entity.Person
import com.skydoves.themovies.models.network.PersonDetail
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_person_detail.*
import kotlinx.android.synthetic.main.toolbar_default.*
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * Developed by skydoves on 2018-08-16.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class PersonDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(PersonDetailViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_detail)

        initializeUI()
        observeViewModel()
    }

    private fun initializeUI() {
        toolbar_home.setOnClickListener { onBackPressed() }
        toolbar_title.text = getPersonFromIntent().name
        getPersonFromIntent().profile_path?.let {
            Glide.with(this).load(Api.getPosterPath(it))
                    .apply(RequestOptions().circleCrop()).into(person_detail_profile)
        }
        person_detail_name.text = getPersonFromIntent().name
    }

    private fun observeViewModel() {
        observeLiveData(viewModel.getPersonObservable()) { updatePersonDetail(it)}
        viewModel.postPersonId(getPersonFromIntent().id)
    }

    private fun updatePersonDetail(resource: Resource<PersonDetail>) {
        when(resource.status) {
            Status.SUCCESS -> {
                resource.data?.let {
                    person_detail_biography.text = it.biography
                    detail_person_tags.tags = it.also_known_as

                    if(it.also_known_as.isNotEmpty()) {
                        detail_person_tags.visible()
                    }
                }
            }
            Status.ERROR -> toast(resource.errorEnvelope?.status_message.toString())
            Status.LOADING -> { }
        }
    }

    private fun getPersonFromIntent(): Person {
        return intent.getParcelableExtra("person") as Person
    }
}
