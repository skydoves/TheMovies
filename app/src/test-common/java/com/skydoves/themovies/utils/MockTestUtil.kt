package com.skydoves.themovies.utils

import com.skydoves.themovies.models.Keyword
import com.skydoves.themovies.models.Review
import com.skydoves.themovies.models.Video
import com.skydoves.themovies.models.entity.Movie
import com.skydoves.themovies.models.entity.Person
import com.skydoves.themovies.models.entity.Tv
import com.skydoves.themovies.models.network.PersonDetail

/**
 * Developed by skydoves on 2018-08-10.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class MockTestUtil {
    companion object {
        fun mockMovie() = Movie(1, emptyList(), emptyList(), emptyList(), "", false, "", "", ArrayList(), 123, "", "", "", "", 0f, 0, false, 0f)
        fun mockTv() = Tv(1, emptyList(), emptyList(), emptyList(), "", 0f, 123, "", 0f, "", "", ArrayList(), ArrayList(), "", 1, "", "")
        fun mockPerson() = Person(1, mockPersonDetail(), "", false, 123, "", 0f)
        fun mockKeywordList(): List<Keyword> {
            val keywords = ArrayList<Keyword>()
            keywords.add(Keyword(100, "keyword0"))
            keywords.add(Keyword(101, "keyword1"))
            keywords.add(Keyword(102, "keyword2"))
            return keywords
        }
        fun mockVideoList(): List<Video> {
            val videos = ArrayList<Video>()
            videos.add(Video("123", "video0", "", "", 0, ""))
            videos.add(Video("123", "video0", "", "", 0, ""))
            return videos
        }
        fun mockReviewList(): List<Review> {
            val reviews = ArrayList<Review>()
            reviews.add(Review("123", "", "", ""))
            reviews.add(Review("123", "", "", ""))
            return reviews
        }
        fun mockPersonDetail(): PersonDetail {
            return PersonDetail("", "", "", emptyList(), "")
        }
    }
}
