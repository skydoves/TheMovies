package com.skydoves.themovies.mappers

import com.skydoves.themovies.models.network.PersonDetail

/**
 * Developed by skydoves on 2018-08-17.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class PersonDetailResponseMapper: NetworkResponseMapper<PersonDetail> {
    override fun onLastPage(response: PersonDetail): Boolean {
        return true
    }
}
