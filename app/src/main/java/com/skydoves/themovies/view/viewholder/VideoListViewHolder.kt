package com.skydoves.themovies.view.viewholder

import android.view.View
import com.bumptech.glide.Glide
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.skydoves.themovies.api.Api
import com.skydoves.themovies.models.Video
import kotlinx.android.synthetic.main.item_video.view.*

/**
 * Developed by skydoves on 2018-08-19.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class VideoListViewHolder(val view: View, private val delegate: Delegate): BaseViewHolder(view) {

    interface Delegate {
        fun onItemClicked(video: Video)
    }

    private lateinit var video: Video

    override fun bindData(data: Any) {
        if(data is Video) {
            video = data
            drawItem()
        }
    }

    private fun drawItem() {
        itemView.run {
            item_video_title.text = video.name
            Glide.with(context)
                    .load(Api.getYoutubeThumbnailPath(video.key))
                    .listener(GlidePalette.with(Api.getYoutubeThumbnailPath(video.key))
                            .use(BitmapPalette.Profile.VIBRANT)
                            .intoBackground(item_video_palette)
                            .crossfade(true))
                    .into(item_video_cover)
        }
    }

    override fun onClick(v: View?) {
        delegate.onItemClicked(video)
    }

    override fun onLongClick(v: View?) = false
}
