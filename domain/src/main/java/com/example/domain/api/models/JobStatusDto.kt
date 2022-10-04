package com.example.domain.api.models

import com.squareup.moshi.Json

data class JobStatusDto(
    @Json(name = "status")
    val status: String? = null,
)
