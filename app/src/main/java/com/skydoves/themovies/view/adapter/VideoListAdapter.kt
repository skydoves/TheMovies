package com.skydoves.themovies.view.adapter

import android.view.View
import com.skydoves.baserecyclerviewadapter.BaseAdapter
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.skydoves.baserecyclerviewadapter.SectionRow
import com.skydoves.themovies.R
import com.skydoves.themovies.models.Resource
import com.skydoves.themovies.models.Video
import com.skydoves.themovies.view.viewholder.VideoListViewHolder

/**
 * Developed by skydoves on 2018-08-19.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class VideoListAdapter(private val delegate: VideoListViewHolder.Delegate): BaseAdapter() {

    init {
        addSection(ArrayList<Video>())
    }

    fun addVideoList(resource: Resource<List<Video>>) {
        resource.data?.let {
            sections[0].addAll(it)
        }
        notifyDataSetChanged()
    }

    override fun layout(sectionRow: SectionRow): Int {
        return R.layout.item_video
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return VideoListViewHolder(view, delegate)
    }
}
