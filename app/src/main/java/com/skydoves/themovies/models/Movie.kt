package com.skydoves.themovies.models

import android.arch.persistence.room.Entity
import android.os.Parcel
import android.os.Parcelable

/**
 * Developed by skydoves on 2018-08-08.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@Entity(primaryKeys = [("id")])
data class Movie(var page: Int,
                 val poster_path: String?,
                 val adult: Boolean,
                 val overview: String,
                 val release_date: String,
                 val genre_ids: List<Int>,
                 val id: Int,
                 val original_title: String,
                 val original_language: String,
                 val title: String,
                 val backdrop_path: String?,
                 val popularity: Float,
                 val vote_count: Int,
                 val video: Boolean,
                 val vote_average: Float) : Parcelable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            1 == source.readInt(),
            source.readString(),
            source.readString(),
            ArrayList<Int>().apply { source.readList(this, Int::class.java.classLoader) },
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readFloat(),
            source.readInt(),
            1 == source.readInt(),
            source.readFloat()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(page)
        writeString(poster_path)
        writeInt((if (adult) 1 else 0))
        writeString(overview)
        writeString(release_date)
        writeList(genre_ids)
        writeInt(id)
        writeString(original_title)
        writeString(original_language)
        writeString(title)
        writeString(backdrop_path)
        writeFloat(popularity)
        writeInt(vote_count)
        writeInt((if (video) 1 else 0))
        writeFloat(vote_average)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Movie> = object : Parcelable.Creator<Movie> {
            override fun createFromParcel(source: Parcel): Movie = Movie(source)
            override fun newArray(size: Int): Array<Movie?> = arrayOfNulls(size)
        }
    }
}
