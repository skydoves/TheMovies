package com.skydoves.themovies.models

import android.arch.persistence.room.Entity
import android.os.Parcel
import android.os.Parcelable

/**
 * Developed by skydoves on 2018-08-07.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@Entity(primaryKeys = [("id")])
data class Tv(var page: Int,
              val poster_path: String,
              val popularity: Float,
              val id: Int,
              val backdrop_path: String?,
              val vote_average: Float,
              val overview: String,
              val first_air_date: String,
              val origin_country: List<String>,
              val genre_ids: List<Int>,
              val original_language: String,
              val vote_count: Int,
              val name: String,
              val original_name: String) : Parcelable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            source.readFloat(),
            source.readInt(),
            source.readString(),
            source.readFloat(),
            source.readString(),
            source.readString(),
            source.createStringArrayList(),
            ArrayList<Int>().apply { source.readList(this, Int::class.java.classLoader) },
            source.readString(),
            source.readInt(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(page)
        writeString(poster_path)
        writeFloat(popularity)
        writeInt(id)
        writeString(backdrop_path)
        writeFloat(vote_average)
        writeString(overview)
        writeString(first_air_date)
        writeStringList(origin_country)
        writeList(genre_ids)
        writeString(original_language)
        writeInt(vote_count)
        writeString(name)
        writeString(original_name)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Tv> = object : Parcelable.Creator<Tv> {
            override fun createFromParcel(source: Parcel): Tv = Tv(source)
            override fun newArray(size: Int): Array<Tv?> = arrayOfNulls(size)
        }
    }
}
