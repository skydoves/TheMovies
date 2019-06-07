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
package com.skydoves.themovies.db

import com.skydoves.themovies.models.entity.Tv
import com.skydoves.themovies.utils.LiveDataTestUtil
import com.skydoves.themovies.utils.MockTestUtil.Companion.mockTv
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TvDaoTest : DbTest() {

  @Test
  fun insertAndRead() {
    val tvList = ArrayList<Tv>()
    val tv = mockTv()
    tvList.add(tv)

    db.tvDao().insertTv(tvList)
    val loadFromDB = LiveDataTestUtil.getValue(db.tvDao().getTvList(tv.page))[0]
    MatcherAssert.assertThat(loadFromDB.page, CoreMatchers.`is`(1))
    MatcherAssert.assertThat(loadFromDB.id, CoreMatchers.`is`(123))
  }

  @Test
  fun updateAndReadTest() {
    val tvList = ArrayList<Tv>()
    val tv = mockTv()
    tvList.add(tv)
    db.tvDao().insertTv(tvList)

    val loadFromDB = db.tvDao().getTv(tv.id)
    assertThat(loadFromDB.page, `is`(1))

    tv.page = 10
    db.tvDao().updateTv(tv)

    val updated = db.tvDao().getTv(tv.id)
    assertThat(updated.page, `is`(10))
  }
}
