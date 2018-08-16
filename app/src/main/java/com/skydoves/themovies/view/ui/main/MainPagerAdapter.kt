package com.skydoves.themovies.view.ui.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Developed by skydoves on 2018-08-11.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class MainPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> return MovieListFragment()
            1 -> return TvListFragment()
            else -> return PersonListFragment()
        }
    }

    override fun getCount() = 3
}
