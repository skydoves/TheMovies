package com.skydoves.themovies.models.network

/**
 * Developed by skydoves on 2018-08-10.
 * Copyright (c) 2018 skydoves rights reserved.
 */

data class ErrorEnvelope(val status_code: Int,
                         val status_message: String,
                         val success: Boolean)
