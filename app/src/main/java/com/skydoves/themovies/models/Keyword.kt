/*
 * The MIT License (MIT)
 *
 * Designed and developed by 2018 skydoves (Jaewoong Eum)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.skydoves.themovies.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Developed by skydoves on 2018-08-16.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "unused")
data class Keyword(
  val id: Int,
  val name: String
) : Parcelable {
  constructor(source: Parcel) : this(
    source.readInt(),
    source.readString()
  )

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
    writeInt(id)
    writeString(name)
  }

  companion object {
    @JvmField
    val CREATOR: Parcelable.Creator<Keyword> = object : Parcelable.Creator<Keyword> {
      override fun createFromParcel(source: Parcel): Keyword = Keyword(source)
      override fun newArray(size: Int): Array<Keyword?> = arrayOfNulls(size)
    }
  }
}
