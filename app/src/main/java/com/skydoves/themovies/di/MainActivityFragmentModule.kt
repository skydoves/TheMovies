package com.skydoves.themovies.di

import com.skydoves.themovies.view.ui.main.MovieListFragment
import com.skydoves.themovies.view.ui.main.StarListFramgment
import com.skydoves.themovies.view.ui.main.TvListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Developed by skydoves on 2018-08-11.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@Module
abstract class MainActivityFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeMovieListFragment(): MovieListFragment

    @ContributesAndroidInjector
    abstract fun contributeTvListFragment(): TvListFragment

    @ContributesAndroidInjector
    abstract fun contributeStarFragment(): StarListFramgment
}
