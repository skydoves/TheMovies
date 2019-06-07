/*
 * The MIT License (MIT)
 *
 * Designed and developed by 2018 skydoves (Jaewoong Eum)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.skydoves.themovies.view.viewholder

import android.view.View
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.skydoves.themovies.models.Review
import kotlinx.android.synthetic.main.item_review.view.*

class ReviewListViewHolder(val view: View) : BaseViewHolder(view) {

  private lateinit var review: Review

  override fun bindData(data: Any) {
    if (data is Review) {
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
