package com.skydoves.themovies.view.ui.main

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.baserecyclerviewadapter.RecyclerViewPaginator
import com.skydoves.themovies.R
import com.skydoves.themovies.extension.observeLiveData
import com.skydoves.themovies.models.Resource
import com.skydoves.themovies.models.Status
import com.skydoves.themovies.models.entity.Person
import com.skydoves.themovies.view.adapter.PeopleAdapter
import com.skydoves.themovies.view.ui.details.person.PersonDetailActivity
import com.skydoves.themovies.view.viewholder.PeopleViewHolder
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.main_fragment_movie.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject

/**
 * Developed by skydoves on 2018-08-11.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class PersonListFragment: Fragment(), PeopleViewHolder.Delegate {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MainActivityViewModel

    private val adapter = PeopleAdapter(this)
    private lateinit var paginator: RecyclerViewPaginator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.main_fragment_star, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeUI()
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java)
        observeViewModel()
    }

    private fun initializeUI() {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        paginator = RecyclerViewPaginator(
                recyclerView = recyclerView,
                isLoading = { viewModel.getPeopleValues()?.status == Status.LOADING },
                loadMore = { loadMore(it) },
                onLast =  { viewModel.getPeopleValues()?.onLastPage!! })
    }

    private fun observeViewModel() {
        observeLiveData(viewModel.getPeopleObservable()) { updatePeople(it) }
        viewModel.postPeoplePage(1)
    }

    private fun updatePeople(resource: Resource<List<Person>>) {
        when(resource.status) {
            Status.SUCCESS -> adapter.addPeople(resource)
            Status.ERROR -> toast(resource.errorEnvelope?.status_message.toString())
            Status.LOADING -> { }
        }
    }

    private fun loadMore(page: Int) {
        viewModel.postPeoplePage(page)
    }

    override fun onItemClick(person: Person, view: View) {
        activity?.let {
            PersonDetailActivity.startActivity(this, it, person, view)
        } ?: startActivity<PersonDetailActivity>("person" to person)
    }
}
