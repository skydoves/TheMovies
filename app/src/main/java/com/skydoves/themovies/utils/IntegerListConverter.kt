package com.skydoves.themovies.utils

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Developed by skydoves on 2018-08-09.
 * Copyright (c) 2018 skydoves rights reserved.
 */

open class IntegerListConverter {
    @TypeConverter
    fun fromString(value: String): List<Int>? {
        val listType = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson<List<Int>>(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Int>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}