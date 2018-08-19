package com.skydoves.themovies.utils

import com.skydoves.themovies.models.Keyword

/**
 * Developed by skydoves on 2018-08-19.
 * Copyright (c) 2018 skydoves rights reserved.
 */

object KeywordListMapper {
    fun mapToStringList(keywords: List<Keyword>): List<String> {
        var list: MutableList<String> = ArrayList()
        for(keyword in keywords) {
            list.add(keyword.name)
        }
        if(list.size > 7) {
            list = list.subList(0, 6)
        }
        return list
    }
}
