package ru.university.network.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.university.network.model.AuthRequestDto
import ru.university.network.model.AuthResponseDto
import ru.university.network.model.SignUpRequestDto
import ru.university.network.model.UserDto

interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body request: AuthRequestDto): AuthResponseDto

    @POST("auth/signup")
    suspend fun signUp(@Body request: SignUpRequestDto): AuthResponseDto

    @GET("auth/me")
    suspend fun getProfile(): UserDto
}