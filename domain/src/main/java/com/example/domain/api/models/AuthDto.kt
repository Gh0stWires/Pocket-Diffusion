package com.example.domain.api.models

import com.squareup.moshi.Json

data class AuthDto(
    @Json(name = "authorization")
    val authorization: String?
)
