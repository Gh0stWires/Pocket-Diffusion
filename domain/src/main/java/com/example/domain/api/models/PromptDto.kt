package com.example.domain.api.models

import com.squareup.moshi.Json

data class PromptDto(
    @Json(name = "text")
    val text: String? = null,
    @Json(name = "authorization")
    val authorization: String? = null,
)
