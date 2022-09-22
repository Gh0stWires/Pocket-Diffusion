package com.example.domain.api.repository

import com.example.domain.api.models.JobResultDto
import com.example.domain.api.models.PromptDto
import javax.inject.Inject

class LocalRepo @Inject constructor(private val api: LocalService) {

    suspend fun statusCheck() = api.getResult()

    suspend fun makeImage(prompt: String) = api.runJob(prompt = PromptDto(prompt))

    suspend fun getLatest() = api.getImage()
}