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

package com.skydoves.themovies.view.ui.details.movie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.skydoves.themovies.R
import com.skydoves.themovies.compose.ViewModelActivity
import com.skydoves.themovies.databinding.ActivityMovieDetailBinding
import com.skydoves.themovies.extension.applyToolbarMargin
import com.skydoves.themovies.extension.simpleToolbarWithHome
import com.skydoves.themovies.models.entity.Movie
import com.skydoves.themovies.view.adapter.ReviewListAdapter
import com.skydoves.themovies.view.adapter.VideoListAdapter
import com.skydoves.whatif.whatIfNotNull
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : ViewModelActivity() {

  private val viewModel: MovieDetailViewModel by injectViewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding<ActivityMovieDetailBinding>(R.layout.activity_movie_detail).run {
      lifecycleOwner = this@MovieDetailActivity
      viewModel = this@MovieDetailActivity.viewModel.apply { postMovieId(getMovieFromIntent().id) }
      movie = getMovieFromIntent()
      videoAdapter = VideoListAdapter()
      reviewAdapter = ReviewListAdapter()
    }
    initializeUI()
  }

  private fun initializeUI() {
    applyToolbarMargin(movie_detail_toolbar)
    simpleToolbarWithHome(movie_detail_toolbar, getMovieFromIntent().title)
  }

  private fun getMovieFromIntent() = intent.getParcelableExtra(movieId) as Movie

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    if (item?.itemId == android.R.id.home) onBackPressed()
    return false
  }

  companion object {
    private const val movieId = "movie"
    fun startActivityModel(context: Context?, movie: Movie) {
      context.whatIfNotNull {
        val intent = Intent(it, MovieDetailActivity::class.java).apply { putExtra(movieId, movie) }
        it.startActivity(intent)
      }
    }
  }
}
