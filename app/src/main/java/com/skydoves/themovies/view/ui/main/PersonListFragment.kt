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

package com.skydoves.themovies.view.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.baserecyclerviewadapter.RecyclerViewPaginator
import com.skydoves.themovies.R
import com.skydoves.themovies.compose.ViewModelFragment
import com.skydoves.themovies.databinding.MainFragmentStarBinding
import com.skydoves.themovies.models.Status
import com.skydoves.themovies.models.entity.Person
import com.skydoves.themovies.view.adapter.PeopleAdapter
import com.skydoves.themovies.view.ui.details.person.PersonDetailActivity
import com.skydoves.themovies.view.viewholder.PeopleViewHolder

class PersonListFragment : ViewModelFragment(), PeopleViewHolder.Delegate {

  private val viewModel: MainActivityViewModel by injectActivityVIewModels()
  private lateinit var binding: MainFragmentStarBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = binding<MainFragmentStarBinding>(inflater, R.layout.main_fragment_star, container)
      .apply {
        viewModel = this@PersonListFragment.viewModel
        lifecycleOwner = this@PersonListFragment
        adapter = PeopleAdapter(this@PersonListFragment)
      }
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    RecyclerViewPaginator(
      recyclerView = binding.recyclerView,
      isLoading = { viewModel.getPeopleValues()?.status == Status.LOADING },
      loadMore = { loadMore(it) },
      onLast = { viewModel.getPeopleValues()?.onLastPage!! }
    ).apply {
      currentPage = 1
    }
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    loadMore(page = 1)
  }

  private fun loadMore(page: Int) = viewModel.postPeoplePage(page)

  override fun onItemClick(person: Person, view: View) =
    PersonDetailActivity.startActivity(activity, person, view)
}
