package com.skydoves.themovies.models.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.os.Parcel
import android.os.Parcelable
import com.skydoves.themovies.models.network.PersonDetail

/**
 * Developed by skydoves on 2018-08-15.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@Entity(tableName = "People", primaryKeys = ["id"])
data class Person(var page: Int,
                  @Embedded var personDetail: PersonDetail? = null,
                  val profile_path: String?,
                  val adult: Boolean,
                  val id: Int,
                  val name: String,
                  val popularity: Float) : Parcelable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readParcelable<PersonDetail>(PersonDetail::class.java.classLoader),
            source.readString(),
            1 == source.readInt(),
            source.readInt(),
            source.readString(),
            source.readFloat()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(page)
        writeParcelable(personDetail, 0)
        writeString(profile_path)
        writeInt((if (adult) 1 else 0))
        writeInt(id)
        writeString(name)
        writeFloat(popularity)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Person> = object : Parcelable.Creator<Person> {
            override fun createFromParcel(source: Parcel): Person = Person(source)
            override fun newArray(size: Int): Array<Person?> = arrayOfNulls(size)
        }
    }
}