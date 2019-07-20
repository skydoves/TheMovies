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
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.themovies.R
import com.skydoves.themovies.api.Api
import com.skydoves.themovies.databinding.ActivityMovieDetailBinding
import com.skydoves.themovies.extension.activityBinding
import com.skydoves.themovies.extension.applyToolbarMargin
import com.skydoves.themovies.extension.simpleToolbarWithHome
import com.skydoves.themovies.extension.viewModel
import com.skydoves.themovies.extension.vmDelegate
import com.skydoves.themovies.models.Video
import com.skydoves.themovies.models.entity.Movie
import com.skydoves.themovies.view.adapter.ReviewListAdapter
import com.skydoves.themovies.view.adapter.VideoListAdapter
import com.skydoves.themovies.view.viewholder.VideoListViewHolder
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.layout_movie_detail_body.*
import org.jetbrains.anko.startActivity
import javax.inject.Inject

class MovieDetailActivity : AppCompatActivity(), VideoListViewHolder.Delegate {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory
  private val vmDelegate by vmDelegate(MovieDetailViewModel::class)
  private val binding by activityBinding<ActivityMovieDetailBinding>(R.layout.activity_movie_detail)

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this@MovieDetailActivity)
    super.onCreate(savedInstanceState)
    val vm = viewModel(vmDelegate, viewModelFactory)
    vm.postMovieId(getMovieFromIntent().id)
    with(binding) {
      lifecycleOwner = this@MovieDetailActivity
      viewModel = vm
      detailBody.viewModel = vm
      movie = getMovieFromIntent()
      detailHeader.movie = getMovieFromIntent()
      detailBody.movie = getMovieFromIntent()
    }
    initializeUI()
  }

  private fun initializeUI() {
    applyToolbarMargin(movie_detail_toolbar)
    simpleToolbarWithHome(movie_detail_toolbar, getMovieFromIntent().title)
    detail_body_recyclerView_trailers.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    detail_body_recyclerView_trailers.adapter = VideoListAdapter(this)
    detail_body_recyclerView_reviews.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    detail_body_recyclerView_reviews.adapter = ReviewListAdapter()
    detail_body_recyclerView_reviews.isNestedScrollingEnabled = false
    detail_body_recyclerView_reviews.setHasFixedSize(true)
  }

  private fun getMovieFromIntent(): Movie {
    return intent.getParcelableExtra(movieId) as Movie
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    if (item?.itemId == android.R.id.home) onBackPressed()
    return false
  }

  override fun onItemClicked(video: Video) {
    val playVideoIntent = Intent(Intent.ACTION_VIEW, Uri.parse(Api.getYoutubeVideoPath(video.key)))
    startActivity(playVideoIntent)
  }

  companion object {
    private const val movieId = "movie"
    fun startActivityModel(context: Context?, movie: Movie) {
      context?.startActivity<MovieDetailActivity>(movieId to movie)
    }
  }
}
