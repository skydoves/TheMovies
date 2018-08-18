package com.skydoves.themovies.utils

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.skydoves.themovies.models.Video

/**
 * Developed by skydoves on 2018-08-09.
 * Copyright (c) 2018 skydoves rights reserved.
 */

open class VideoListConverter {
    @TypeConverter
    fun fromString(value: String): List<Video>? {
        val listType = object : TypeToken<List<Video>>() {}.type
        return Gson().fromJson<List<Video>>(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Video>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}