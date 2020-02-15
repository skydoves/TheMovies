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

package com.skydoves.themovies.view.ui.main

import android.os.SystemClock
import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.skydoves.themovies.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MovieDetailActivityTest {

  @Rule
  @JvmField
  var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

  @Test
  fun movieDetailActivityTest() {
    val relativeLayout = onView(
        allOf(childAtPosition(
            allOf(withId(R.id.recyclerView),
                childAtPosition(
                    withClassName(`is`("android.widget.RelativeLayout")),
                    0)),
            0),
            isDisplayed()))
    SystemClock.sleep(2000)
    relativeLayout.perform(click())

    val textView = onView(
        allOf(withId(R.id.detail_body_trailers), withText("Trailers"),
            childAtPosition(
                childAtPosition(
                    IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java),
                    1),
                0),
            isDisplayed()))
    SystemClock.sleep(2000)
    textView.check(matches(withText("Trailers")))

    val textView2 = onView(
        allOf(withText("Summary"),
            childAtPosition(
                childAtPosition(
                    IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java),
                    1),
                2),
            isDisplayed()))
    textView2.check(matches(withText("Summary")))

    val appCompatImageButton = onView(
        allOf(withContentDescription("Navigate up"),
            childAtPosition(
                allOf(withId(R.id.movie_detail_toolbar),
                    childAtPosition(
                        withContentDescription("Aquaman"),
                        1)),
                1),
            isDisplayed()))
    appCompatImageButton.perform(click())
  }

  private fun childAtPosition(
    parentMatcher: Matcher<View>,
    position: Int
  ): Matcher<View> {

    return object : TypeSafeMatcher<View>() {
      override fun describeTo(description: Description) {
        description.appendText("Child at position $position in parent ")
        parentMatcher.describeTo(description)
      }

      public override fun matchesSafely(view: View): Boolean {
        val parent = view.parent
        return parent is ViewGroup && parentMatcher.matches(parent) &&
            view == parent.getChildAt(position)
      }
    }
  }
}
