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
package com.skydoves.themovies.models.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import com.skydoves.themovies.models.Keyword
import com.skydoves.themovies.models.Review
import com.skydoves.themovies.models.Video

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "unused")
@Entity(primaryKeys = [("id")])
data class Movie(
  var page: Int,
  var keywords: List<Keyword>? = ArrayList(),
  var videos: List<Video>? = ArrayList(),
  var reviews: List<Review>? = ArrayList(),
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
  val vote_average: Float
) : Parcelable {
  constructor(source: Parcel) : this(
    source.readInt(),
    ArrayList<Keyword>().apply { source.readList(this, Keyword::class.java.classLoader) },
    ArrayList<Video>().apply { source.readList(this, Video::class.java.classLoader) },
    ArrayList<Review>().apply { source.readList(this, Review::class.java.classLoader) },
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
    writeList(keywords)
    writeList(videos)
    writeList(reviews)
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
