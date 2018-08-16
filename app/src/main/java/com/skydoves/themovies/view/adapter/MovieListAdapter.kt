package com.skydoves.themovies.view.adapter

import android.view.View
import com.skydoves.baserecyclerviewadapter.BaseAdapter
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.skydoves.baserecyclerviewadapter.SectionRow
import com.skydoves.themovies.R
import com.skydoves.themovies.models.entity.Movie
import com.skydoves.themovies.models.Resource
import com.skydoves.themovies.view.viewholder.MovieListViewHolder

/**
 * Developed by skydoves on 2018-08-12.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class MovieListAdapter(private val delegate: MovieListViewHolder.Delegate): BaseAdapter() {

    init {
        addSection(ArrayList<Movie>())
    }

    fun addMovieList(resource: Resource<List<Movie>>) {
        resource.data?.let {
            sections[0].addAll(it)
            notifyDataSetChanged()
        }
    }

    override fun layout(sectionRow: SectionRow): Int {
        return R.layout.item_poster
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return MovieListViewHolder(view, delegate)
    }
}
