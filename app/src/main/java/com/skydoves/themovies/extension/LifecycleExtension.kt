package com.skydoves.themovies.extension

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer

/**
 * Developed by skydoves on 2018-08-19.
 * Copyright (c) 2018 skydoves rights reserved.
 */

inline fun <T> LifecycleOwner.observeLiveData(data: LiveData<T>, crossinline onChanged: (T) -> Unit) {
    data.observe(this, Observer {
        it?.let { onChanged(it) }
    })
}

