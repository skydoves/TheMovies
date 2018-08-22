package com.skydoves.themovies.api

import android.arch.lifecycle.LiveData
import com.skydoves.themovies.models.network.PeopleResponse
import com.skydoves.themovies.models.network.PersonDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Developed by skydoves on 2018-08-15.
 * Copyright (c) 2018 skydoves rights reserved.
 */

interface PeopleService {
    /**
     * [People Popular](https://developers.themoviedb.org/3/people/get-popular-people)
     *
     * Get the list of popular people on TMDb. This list updates daily.
     *
     * @param [page] Specify the page of results to query.
     *
     * @return [PeopleResponse] response
     */
    @GET("/3/person/popular?language=en")
    fun fetchPopularPeople(@Query("page") page: Int): LiveData<ApiResponse<PeopleResponse>>

    /**
     * [Person Detail](https://developers.themoviedb.org/3/people/get-person-details)
     *
     * Get the primary person details by id.
     *
     * @para [id] Specify the id of results to query.
     *
     * @return [PersonDetail] response
     */
    @GET("/3/person/{person_id}")
    fun fetchPersonDetail(@Path("person_id") id: Int): LiveData<ApiResponse<PersonDetail>>
}
