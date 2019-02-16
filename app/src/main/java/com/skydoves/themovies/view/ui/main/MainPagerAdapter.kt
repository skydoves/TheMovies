package com.skydoves.themovies.view.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * Developed by skydoves on 2018-08-11.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class MainPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

  override fun getItem(position: Int): Fragment {
    when (position) {
      0 -> return MovieListFragment()
      1 -> return TvListFragment()
      else -> return PersonListFragment()
    }
  }

  override fun getCount() = 3
}
