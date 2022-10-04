package com.example.domain.api.models

import com.squareup.moshi.Json

data class MessageDto(
    @Json(name = "message")
    val message: String? = null
)
