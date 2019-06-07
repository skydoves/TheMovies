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
    return when (position) {
      0 -> MovieListFragment()
      1 -> TvListFragment()
      else -> PersonListFragment()
    }
  }

  override fun getCount() = 3
}
