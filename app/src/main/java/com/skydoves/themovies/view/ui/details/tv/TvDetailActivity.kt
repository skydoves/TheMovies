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

package com.skydoves.themovies.view.ui.details.tv

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.skydoves.themovies.R
import com.skydoves.themovies.compose.ViewModelActivity
import com.skydoves.themovies.databinding.ActivityTvDetailBinding
import com.skydoves.themovies.extension.applyToolbarMargin
import com.skydoves.themovies.extension.simpleToolbarWithHome
import com.skydoves.themovies.models.entity.Tv
import com.skydoves.themovies.view.adapter.ReviewListAdapter
import com.skydoves.themovies.view.adapter.VideoListAdapter
import com.skydoves.whatif.whatIfNotNull
import kotlinx.android.synthetic.main.activity_tv_detail.*

class TvDetailActivity : ViewModelActivity() {

  private val viewModel: TvDetailViewModel by injectViewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding<ActivityTvDetailBinding>(R.layout.activity_tv_detail).run {
      lifecycleOwner = this@TvDetailActivity
      viewModel = this@TvDetailActivity.viewModel.apply { postTvId(getTvFromIntent().id) }
      tv = getTvFromIntent()
      videoAdapter = VideoListAdapter()
      reviewAdapter = ReviewListAdapter()
    }
    initializeUI()
  }

  private fun initializeUI() {
    applyToolbarMargin(tv_detail_toolbar)
    simpleToolbarWithHome(tv_detail_toolbar, getTvFromIntent().name)
  }

  private fun getTvFromIntent() = intent.getParcelableExtra(tvId) as Tv

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    if (item?.itemId == android.R.id.home) onBackPressed()
    return false
  }

  companion object {
    private const val tvId = "tv"
    fun startActivityModel(context: Context?, tv: Tv) {
      context.whatIfNotNull {
        val intent = Intent(it, TvDetailActivity::class.java).apply { putExtra(tvId, tv) }
        it.startActivity(intent)
      }
    }
  }
}
