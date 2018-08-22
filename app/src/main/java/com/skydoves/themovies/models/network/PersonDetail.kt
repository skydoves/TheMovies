package com.skydoves.themovies.models.network

import android.os.Parcel
import android.os.Parcelable
import com.skydoves.themovies.models.NetworkResponseModel

/**
 * Developed by skydoves on 2018-08-22.
 * Copyright (c) 2018 skydoves rights reserved.
 */

data class PersonDetail(val birthday: String,
                        val known_for_department: String,
                        val place_of_birth: String,
                        val also_known_as: List<String>,
                        val biography: String) : Parcelable, NetworkResponseModel {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.createStringArrayList(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(birthday)
        writeString(known_for_department)
        writeString(place_of_birth)
        writeStringList(also_known_as)
        writeString(biography)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<PersonDetail> = object : Parcelable.Creator<PersonDetail> {
            override fun createFromParcel(source: Parcel): PersonDetail = PersonDetail(source)
            override fun newArray(size: Int): Array<PersonDetail?> = arrayOfNulls(size)
        }
    }
}