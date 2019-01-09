package com.skydoves.themovies.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.skydoves.themovies.models.entity.Movie
import com.skydoves.themovies.models.entity.Person
import com.skydoves.themovies.models.entity.Tv
import com.skydoves.themovies.utils.*

/**
 * Developed by skydoves on 2018-08-09.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@Database(entities = [(Movie::class), (Tv::class), (Person::class)], version = 3, exportSchema = false)
@TypeConverters(value = [(StringListConverter::class), (IntegerListConverter::class),
    (KeywordListConverter::class), (VideoListConverter::class), (ReviewListConverter::class)])
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun tvDao(): TvDao
    abstract fun peopleDao(): PeopleDao
}
