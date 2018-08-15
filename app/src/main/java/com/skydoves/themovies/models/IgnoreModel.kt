package com.skydoves.themovies.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Developed by skydoves on 2018-08-15.
 * Copyright (c) 2018 skydoves rights reserved.
 */

data class IgnoreModel(val tag: String) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(tag)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<IgnoreModel> = object : Parcelable.Creator<IgnoreModel> {
            override fun createFromParcel(source: Parcel): IgnoreModel = IgnoreModel(source)
            override fun newArray(size: Int): Array<IgnoreModel?> = arrayOfNulls(size)
        }
    }
}
