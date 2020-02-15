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

package com.skydoves.themovies.api

object Api {
  private const val BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w342"
  private const val BASE_BACKDROP_PATH = "https://image.tmdb.org/t/p/w780"
  private const val YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v="
  private const val YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/"

  fun getPosterPath(posterPath: String): String {
    return BASE_POSTER_PATH + posterPath
  }

  fun getBackdropPath(backdropPath: String): String {
    return BASE_BACKDROP_PATH + backdropPath
  }

  fun getYoutubeVideoPath(videoPath: String): String {
    return YOUTUBE_VIDEO_URL + videoPath
  }

  fun getYoutubeThumbnailPath(thumbnailPath: String): String {
    return "$YOUTUBE_THUMBNAIL_URL$thumbnailPath/default.jpg"
  }
}
