package com.skydoves.themovies.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Developed by skydoves on 2018-08-17.
 * Copyright (c) 2018 skydoves rights reserved.
 */

data class Video(val id: String,
                 val name: String,
                 val site: String,
                 val key: String,
                 val size: Int,
                 val type: String) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readInt(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(name)
        writeString(site)
        writeString(key)
        writeInt(size)
        writeString(type)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Video> = object : Parcelable.Creator<Video> {
            override fun createFromParcel(source: Parcel): Video = Video(source)
            override fun newArray(size: Int): Array<Video?> = arrayOfNulls(size)
        }
    }
}
