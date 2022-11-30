package com.example.domain.api.models

import com.squareup.moshi.Json

data class TokenDto(
    @Json(name = "token")
    val token: String? = null,
    @Json(name = "message")
    val message: String? = null
)
