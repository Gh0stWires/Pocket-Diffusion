package com.example.domain.api.models

import com.squareup.moshi.Json

data class SignInDto(
    @Json(name = "email")
    val email: String? = null,
    @Json(name = "password")
    val password: String? = null,
)
