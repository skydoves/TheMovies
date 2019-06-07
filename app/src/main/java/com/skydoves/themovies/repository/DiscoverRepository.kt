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
package com.skydoves.themovies.repository

import androidx.lifecycle.LiveData
import com.skydoves.themovies.api.ApiResponse
import com.skydoves.themovies.api.TheDiscoverService
import com.skydoves.themovies.mappers.MovieResponseMapper
import com.skydoves.themovies.mappers.TvResponseMapper
import com.skydoves.themovies.models.Resource
import com.skydoves.themovies.models.entity.Movie
import com.skydoves.themovies.models.entity.Tv
import com.skydoves.themovies.models.network.DiscoverMovieResponse
import com.skydoves.themovies.models.network.DiscoverTvResponse
import com.skydoves.themovies.room.MovieDao
import com.skydoves.themovies.room.TvDao
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DiscoverRepository @Inject
constructor(
  val discoverService: TheDiscoverService,
  val movieDao: MovieDao,
  val tvDao: TvDao
) : Repository {

  init {
    Timber.d("Injection DiscoverRepository")
  }

  fun loadMovies(page: Int): LiveData<Resource<List<Movie>>> {
    return object : NetworkBoundRepository<List<Movie>, DiscoverMovieResponse, MovieResponseMapper>() {
      override fun saveFetchData(items: DiscoverMovieResponse) {
        for (item in items.results) {
          item.page = page
        }
        movieDao.insertMovieList(movies = items.results)
      }

      override fun shouldFetch(data: List<Movie>?): Boolean {
        return data == null || data.isEmpty()
      }

      override fun loadFromDb(): LiveData<List<Movie>> {
        return movieDao.getMovieList(page_ = page)
      }

      override fun fetchService(): LiveData<ApiResponse<DiscoverMovieResponse>> {
        return discoverService.fetchDiscoverMovie(page = page)
      }

      override fun mapper(): MovieResponseMapper {
        return MovieResponseMapper()
      }

      override fun onFetchFailed(message: String?) {
        Timber.d("onFetchFailed $message")
      }
    }.asLiveData()
  }

  fun loadTvs(page: Int): LiveData<Resource<List<Tv>>> {
    return object : NetworkBoundRepository<List<Tv>, DiscoverTvResponse, TvResponseMapper>() {
      override fun saveFetchData(items: DiscoverTvResponse) {
        for (item in items.results) {
          item.page = page
        }
        tvDao.insertTv(tvs = items.results)
      }

      override fun shouldFetch(data: List<Tv>?): Boolean {
        return data == null || data.isEmpty()
      }

      override fun loadFromDb(): LiveData<List<Tv>> {
        return tvDao.getTvList(page_ = page)
      }

      override fun fetchService(): LiveData<ApiResponse<DiscoverTvResponse>> {
        return discoverService.fetchDiscoverTv(page = page)
      }

      override fun mapper(): TvResponseMapper {
        return TvResponseMapper()
      }

      override fun onFetchFailed(message: String?) {
        Timber.d("oFetchFailed $message")
      }
    }.asLiveData()
  }
}
