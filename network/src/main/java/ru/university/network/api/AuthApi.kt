package ru.university.network.api

import retrofit2.http.Body
import retrofit2.http.POST
import ru.university.network.model.AuthRequestDto
import ru.university.network.model.AuthResponseDto

interface AuthApi {
    @POST("/auth/signin")
    suspend fun signIn(@Body request: AuthRequestDto): AuthResponseDto

    @POST("/auth/signup")
    suspend fun signUp(@Body request: AuthRequestDto): AuthResponseDto
}