package com.skydoves.themovies.room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.skydoves.themovies.models.entity.Tv

/**
 * Developed by skydoves on 2018-08-09.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@Dao
interface TvDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTv(tvs: List<Tv>)

    @Update
    fun updateTv(tv: Tv)

    @Query("SELECT * FROM Tv WHERE id = :id_")
    fun getTv(id_: Int): Tv

    @Query("SELECT * FROM Tv WHERE page = :page_")
    fun getTvList(page_: Int) : LiveData<List<Tv>>
}
