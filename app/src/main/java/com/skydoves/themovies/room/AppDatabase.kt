package com.skydoves.themovies.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.skydoves.themovies.models.Movie
import com.skydoves.themovies.models.Person
import com.skydoves.themovies.models.Tv
import com.skydoves.themovies.utils.IgnoreModelConverter
import com.skydoves.themovies.utils.IntegerListConverter
import com.skydoves.themovies.utils.StringListConverter

/**
 * Developed by skydoves on 2018-08-09.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@Database(entities = [(Movie::class), (Tv::class), (Person::class)], version = 3, exportSchema = false)
@TypeConverters(value = [(StringListConverter::class), (IntegerListConverter::class), ( IgnoreModelConverter::class)])
abstract class AppDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun tvDao(): TvDao
    abstract fun peopleDao(): PeopleDao
}
