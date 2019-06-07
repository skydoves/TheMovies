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
package com.skydoves.themovies.utils

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.skydoves.themovies.R
import devlight.io.library.ntb.NavigationTabBar

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
