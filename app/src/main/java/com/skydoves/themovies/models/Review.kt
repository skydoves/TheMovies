package com.skydoves.themovies.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Developed by skydoves on 2018-08-17.
 * Copyright (c) 2018 skydoves rights reserved.
 */

data class Review(val id: String,
                  val author: String,
                  val content: String,
                  val url: String) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(author)
        writeString(content)
        writeString(url)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Review> = object : Parcelable.Creator<Review> {
            override fun createFromParcel(source: Parcel): Review = Review(source)
            override fun newArray(size: Int): Array<Review?> = arrayOfNulls(size)
        }
    }
}
