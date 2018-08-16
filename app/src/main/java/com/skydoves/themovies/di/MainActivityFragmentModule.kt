package com.skydoves.themovies.di

import com.skydoves.themovies.view.ui.main.MovieListFragment
import com.skydoves.themovies.view.ui.main.PersonListFragment
import com.skydoves.themovies.view.ui.main.TvListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Developed by skydoves on 2018-08-11.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@Suppress("unused")
@Module
abstract class MainActivityFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeMovieListFragment(): MovieListFragment

    @ContributesAndroidInjector
    abstract fun contributeTvListFragment(): TvListFragment

    @ContributesAndroidInjector
    abstract fun contributePersonListFragment(): PersonListFragment
}
