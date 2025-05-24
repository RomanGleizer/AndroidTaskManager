package ru.university.domain.model

import kotlinx.datetime.LocalDateTime

data class Project(
    val id: String,
    val title: String,
    val description: String?,
    val ownerId: String,
    val members: List<String>,
    val createdAt: LocalDateTime
)
