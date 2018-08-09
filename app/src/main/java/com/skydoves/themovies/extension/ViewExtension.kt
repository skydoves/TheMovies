package com.skydoves.themovies.extension

import android.view.View

/**
 * Developed by skydoves on 2018-08-09.
 * Copyright (c) 2018 skydoves rights reserved.
 */

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.inVisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}