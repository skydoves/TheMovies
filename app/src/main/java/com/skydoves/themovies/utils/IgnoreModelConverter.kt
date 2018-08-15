package com.skydoves.themovies.utils

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.skydoves.themovies.models.IgnoreModel

/**
 * Developed by skydoves on 2018-08-15.
 * Copyright (c) 2018 skydoves rights reserved.
 */

open class IgnoreModelConverter {
    @TypeConverter
    fun fromString(value: String): Any {
        val modelType = object : TypeToken<IgnoreModel>() {}.type
        return Gson().fromJson<IgnoreModel>(value, modelType)
    }

    @TypeConverter
    fun fromModel(obj: Any): String {
        return obj.toString()
    }
}
