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

/**
 * Developed by skydoves on 2018-08-10.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@RunWith(JUnit4::class)
class TvDaoTest: DbTest() {

    @Test fun insertAndRead() {
        val tvList = ArrayList<Tv>()
        val tv = mockTv()
        tvList.add(tv)

        db.tvDao().insertTv(tvList)
        val loadFromDB = LiveDataTestUtil.getValue(db.tvDao().getTvList(tv.page))[0]
        MatcherAssert.assertThat(loadFromDB.page, CoreMatchers.`is`(1))
        MatcherAssert.assertThat(loadFromDB.id, CoreMatchers.`is`(123))
    }

    @Test fun updateAndReadTest() {
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
