package ru.university.network.model

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequestDto(
    val name: String,
    val email: String,
    val password: String
)