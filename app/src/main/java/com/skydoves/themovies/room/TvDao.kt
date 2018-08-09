package com.skydoves.themovies.room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.skydoves.themovies.models.Tv

/**
 * Developed by skydoves on 2018-08-09.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@Dao
interface TvDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTv(tvs: List<Tv>)

    @Query("SELECT * FROM Tv WHERE page = :page_")
    fun getTvList(page_: Int) : LiveData<List<Tv>>
}
