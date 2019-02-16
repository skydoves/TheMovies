package com.skydoves.themovies.db

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.skydoves.themovies.room.AppDatabase
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

/**
 * Developed by skydoves on 2018-08-10.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@RunWith(AndroidJUnit4::class)
abstract class DbTest {
  lateinit var db: AppDatabase

  @Before
  fun initDB() {
    db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().context,
        AppDatabase::class.java).build()
  }

  @After
  fun closeDB() {
    db.close()
  }
}
