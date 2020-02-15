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
import com.skydoves.themovies.databinding.MainFragmentTvBinding
import com.skydoves.themovies.models.Status
import com.skydoves.themovies.models.entity.Tv
import com.skydoves.themovies.view.adapter.TvListAdapter
import com.skydoves.themovies.view.ui.details.tv.TvDetailActivity
import com.skydoves.themovies.view.viewholder.TvListViewHolder

class TvListFragment : ViewModelFragment(), TvListViewHolder.Delegate {

  private val viewModel: MainActivityViewModel by injectActivityVIewModels()
  private lateinit var binding: MainFragmentTvBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = binding<MainFragmentTvBinding>(inflater, R.layout.main_fragment_tv, container)
      .apply {
        viewModel = this@TvListFragment.viewModel
        lifecycleOwner = this@TvListFragment
        adapter = TvListAdapter(this@TvListFragment)
      }
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    RecyclerViewPaginator(
      recyclerView = binding.recyclerView,
      isLoading = { viewModel.getTvListValues()?.status == Status.LOADING },
      loadMore = { loadMore(it) },
      onLast = { viewModel.getTvListValues()?.onLastPage!! }
    ).apply {
      currentPage = 1
    }
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    loadMore(page = 1)
  }

  private fun loadMore(page: Int) = viewModel.postTvPage(page)

  override fun onItemClick(tv: Tv) =
    TvDetailActivity.startActivityModel(context, tv)
}
