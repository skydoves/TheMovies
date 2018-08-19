package com.skydoves.themovies.view.viewholder

import android.view.View
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.skydoves.themovies.models.Review
import kotlinx.android.synthetic.main.item_review.view.*

/**
 * Developed by skydoves on 2018-08-20.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class ReviewListViewHolder(val view: View): BaseViewHolder(view) {

    private lateinit var review: Review

    override fun bindData(data: Any) {
        if(data is Review) {
            review = data
            drawItem()
        }
    }

    private fun drawItem() {
        itemView.run {
            item_review_title.text = review.author
            item_review_content.text = review.content
        }
    }

    override fun onClick(v: View?) {
    }

    override fun onLongClick(v: View?) = false
}
