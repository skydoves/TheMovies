package com.skydoves.themovies.di

import com.skydoves.themovies.view.ui.details.movie.MovieDetailActivity
import com.skydoves.themovies.view.ui.details.person.PersonDetailActivity
import com.skydoves.themovies.view.ui.details.tv.TvDetailActivity
import com.skydoves.themovies.view.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Developed by skydoves on 2018-08-07.
 * Copyright (c) 2018 skydoves rights reserved.
 */

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
