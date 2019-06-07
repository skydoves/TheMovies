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
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.skydoves.themovies.api.Api
import com.skydoves.themovies.models.entity.Person
import kotlinx.android.synthetic.main.item_person.view.*

class PeopleViewHolder(val view: View, private val delegate: Delegate)
  : BaseViewHolder(view) {

  interface Delegate {
    fun onItemClick(person: Person, view: View)
  }

  private lateinit var person: Person

  @Throws(Exception::class)
  override fun bindData(data: Any) {
    if (data is Person) {
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
