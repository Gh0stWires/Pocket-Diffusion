package com.example.domain.api.repository

import com.example.domain.api.models.JobResultDto
import com.example.domain.api.models.JobStatusDto
import com.example.domain.api.models.PromptDto
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LocalService {
    @GET("/get-result")
    suspend fun getResult(): JobStatusDto

    @POST("/make")
    suspend fun runJob(@Body prompt: PromptDto): JobResultDto

    @GET("/get-newest-image")
    suspend fun getImage(): ResponseBody
}
