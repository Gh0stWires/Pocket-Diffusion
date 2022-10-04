package com.example.domain.api.repository

import com.example.domain.api.models.AuthDto
import com.example.domain.api.models.JobResultDto
import com.example.domain.api.models.JobStatusDto
import com.example.domain.api.models.MessageDto
import com.example.domain.api.models.PromptDto
import com.example.domain.api.models.SignInDto
import com.example.domain.api.models.TokenDto
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface LocalService {
    @POST("/get-result")
    suspend fun getResult(@Body auth: AuthDto): JobStatusDto

    @POST("/make")
    suspend fun runJob(@Body prompt: PromptDto): JobResultDto

    @POST("/get-newest-image")
    suspend fun getImage(@Body auth: AuthDto): ResponseBody

    @POST("/api/token")
    suspend fun login(@Body signIn: SignInDto): TokenDto

    @POST("/api/signup")
    suspend fun signup(@Body signIn: SignInDto): MessageDto
}
