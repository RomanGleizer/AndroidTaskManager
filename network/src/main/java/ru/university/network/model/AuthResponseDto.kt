package ru.university.network.model

data class AuthResponseDto(
    val userId: String,
    val token: String,
    val name: String,
    val email: String,
    val inviteId: String
)
