package com.skydoves.themovies.utils

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import com.skydoves.themovies.R
import devlight.io.library.ntb.NavigationTabBar

/**
 * Developed by skydoves on 2018-08-11.
 * Copyright (c) 2018 skydoves rights reserved.
 */

object MainNavigationUtil {

    private fun getNavigationModels(context: Context): ArrayList<NavigationTabBar.Model> {
        val colors = context.resources.getStringArray(R.array.default_preview)
        val models = ArrayList<NavigationTabBar.Model>()
        models.add(
                NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(context, R.drawable.ic_movie_filter_white_24dp),
                        Color.parseColor(colors[0]))
                        .title("movie")
                        .build()
        )
        models.add(
                NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(context, R.drawable.ic_live_tv_white_24dp),
                        Color.parseColor(colors[1]))
                        .title(context.getString(R.string.menu_tv))
                        .build()
        )
        models.add(
                NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(context, R.drawable.ic_star_white_24dp),
                        Color.parseColor(colors[2]))
                        .title(context.getString(R.string.menu_star))
                        .build()
        )
        return models
    }

    fun setComponents(context: Context, viewPager: ViewPager, navigationTabBar: NavigationTabBar) {
        navigationTabBar.models = this.getNavigationModels(context)
        navigationTabBar.setViewPager(viewPager, 0)
    }
}
