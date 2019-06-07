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
package com.skydoves.themovies.di

import com.skydoves.themovies.view.ui.details.movie.MovieDetailActivity
import com.skydoves.themovies.view.ui.details.person.PersonDetailActivity
import com.skydoves.themovies.view.ui.details.tv.TvDetailActivity
import com.skydoves.themovies.view.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityModule {
  @ContributesAndroidInjector(modules = [MainActivityFragmentModule::class])
  internal abstract fun contributeMainActivity(): MainActivity

  @ContributesAndroidInjector
  internal abstract fun contributeMovieDetailActivity(): MovieDetailActivity

  @ContributesAndroidInjector
  internal abstract fun contributeTvDetailActivity(): TvDetailActivity

  @ContributesAndroidInjector
  internal abstract fun contributePersonDetailActivity(): PersonDetailActivity
}
