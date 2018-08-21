package com.skydoves.themovies.room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.skydoves.themovies.models.entity.Person

/**
 * Developed by skydoves on 2018-08-15.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@Dao
interface PeopleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPeople(people: List<Person>)

    @Update
    fun updatePerson(person: Person)

    @Query("SELECT * FROM people WHERE id = :id_")
    fun getPerson(id_: Int): Person

    @Query("SELECT * FROM People WHERE page = :page_")
    fun getPeople(page_: Int): LiveData<List<Person>>
}