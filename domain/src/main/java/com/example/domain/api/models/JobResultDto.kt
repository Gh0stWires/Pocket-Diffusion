package com.example.domain.api.models

import com.squareup.moshi.Json

data class JobResultDto(
    @Json(name = "result")
    val result: String? = null,
    @Json(name = "message")
    val message: String? = null,
)
