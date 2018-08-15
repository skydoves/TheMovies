package com.skydoves.themovies.db

import android.support.test.runner.AndroidJUnit4
import com.skydoves.themovies.models.Person
import com.skydoves.themovies.utils.LiveDataTestUtil
import com.skydoves.themovies.utils.MockTestUtil.Companion.mockPerson
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Developed by skydoves on 2018-08-15.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@RunWith(AndroidJUnit4::class)
class PeopleDaoTest: DbTest() {

    @Test fun insertAndRead() {
        val people = ArrayList<Person>()
        val mockPerson = mockPerson()
        people.add(mockPerson)

        db.peopleDao().insertPeople(people)
        val loadFromDB = LiveDataTestUtil.getValue(db.peopleDao().getPeople(1))[0]
        assertThat(loadFromDB.page, `is`(1))
        assertThat(loadFromDB.id, `is`(123))
    }
}
