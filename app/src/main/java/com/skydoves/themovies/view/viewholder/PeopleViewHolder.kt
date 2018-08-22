package com.skydoves.themovies.view.viewholder

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.skydoves.themovies.api.Api
import com.skydoves.themovies.models.entity.Person
import kotlinx.android.synthetic.main.item_person.view.*

/**
 * Developed by skydoves on 2018-08-15.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class PeopleViewHolder(val view: View, private val delegate: Delegate): BaseViewHolder(view) {

    interface Delegate {
        fun onItemClick(person: Person, view: View)
    }

    private lateinit var person: Person

    @Throws(Exception::class)
    override fun bindData(data: Any) {
        if(data is Person) {
            person = data
            drawItem()
        }
    }

    private fun drawItem() {
        itemView.run {
            item_person_name.text = person.name
            person.profile_path?.let {
                Glide.with(context)
                        .load(Api.getPosterPath(it))
                        .apply(RequestOptions().circleCrop())
                        .into(item_person_profile)
            }
        }
    }

    override fun onClick(p0: View?) {
        delegate.onItemClick(person, itemView.item_person_profile)
    }

    override fun onLongClick(p0: View?) = false
}
