package com.skydoves.themovies.view.adapter

import android.view.View
import com.skydoves.baserecyclerviewadapter.BaseAdapter
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.skydoves.baserecyclerviewadapter.SectionRow
import com.skydoves.themovies.R
import com.skydoves.themovies.models.entity.Person
import com.skydoves.themovies.models.Resource
import com.skydoves.themovies.view.viewholder.PeopleViewHolder

/**
 * Developed by skydoves on 2018-08-15.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class PeopleAdapter(val delegate: PeopleViewHolder.Delegate): BaseAdapter() {

    init {
        addSection(ArrayList<Person>())
    }

    fun addPeople(resource: Resource<List<Person>>) {
        resource.data?.let {
            sections[0].addAll(resource.data)
            notifyDataSetChanged()
        }
    }

    override fun layout(sectionRow: SectionRow): Int {
        return R.layout.item_person
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return PeopleViewHolder(view, delegate)
    }
}
