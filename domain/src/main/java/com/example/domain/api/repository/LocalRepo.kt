package com.example.domain.api.repository

import com.example.domain.api.models.AuthDto
import com.example.domain.api.models.PromptDto
import com.example.domain.api.models.SignInDto
import javax.inject.Inject

class LocalRepo @Inject constructor(private val api: LocalService) {

    suspend fun statusCheck(token: String) = api.getResult(auth = AuthDto(token))

    suspend fun makeImage(prompt: String, token: String) = api.runJob(prompt = PromptDto(prompt, token))

    suspend fun getLatest(token: String) = api.getImage(auth = AuthDto(token))

    suspend fun login(email: String, password: String) = api.login(signIn = SignInDto(email, password))

    suspend fun signUp(email: String, password: String) = api.signup(signIn = SignInDto(email, password))
}
