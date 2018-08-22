package com.skydoves.themovies.api.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.skydoves.themovies.api.PeopleService
import com.skydoves.themovies.api.api.ApiUtil
import com.skydoves.themovies.models.Resource
import com.skydoves.themovies.models.network.PersonDetail
import com.skydoves.themovies.repository.PeopleRepository
import com.skydoves.themovies.room.PeopleDao
import com.skydoves.themovies.utils.MockTestUtil
import com.skydoves.themovies.utils.MockTestUtil.Companion.mockPerson
import com.skydoves.themovies.utils.MockTestUtil.Companion.mockPersonDetail
import com.skydoves.themovies.view.ui.details.person.PersonDetailViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Developed by skydoves on 2018-08-22.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@RunWith(JUnit4::class)
class PersonDetailViewModelTest {

    private lateinit var viewModel: PersonDetailViewModel

    private lateinit var repository: PeopleRepository
    private val peopleDao = mock<PeopleDao>()
    private val service = mock<PeopleService>()

    @Rule
    @JvmField val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        repository = PeopleRepository(service, peopleDao)
        viewModel = PersonDetailViewModel(repository)
    }

    @Test
    fun loadPersonDetail() {
        val loadFromDB = mockPerson()
        whenever(peopleDao.getPerson(123)).thenReturn(loadFromDB)

        val mockResponse = mockPersonDetail()
        val call = ApiUtil.successCall(mockResponse)
        whenever(service.fetchPersonDetail(123)).thenReturn(call)

        val data = repository.loadPersonDetail(123)
        val observer = mock<Observer<Resource<PersonDetail>>>()
        data.observeForever(observer)

        viewModel.postPersonId(123)
        verify(peopleDao, times(3)).getPerson(123)
        verify(observer).onChanged(
                Resource.success(MockTestUtil.mockPersonDetail(), true))

        val updatedPerson = MockTestUtil.mockPerson()
        updatedPerson.personDetail = MockTestUtil.mockPersonDetail()
        verify(peopleDao).updatePerson(updatedPerson)
    }
}
