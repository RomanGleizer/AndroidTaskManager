package ru.university.domain.model

data class User(
    val id: String,
    val name: String,
    val email: String,
    val inviteId: String? = null
)
