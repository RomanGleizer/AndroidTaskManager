package ru.university.network.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateTaskRequestDto(
    val title: String,
    val description: String?    = null,
    val assignedToId: String,
    val dueDate: String?        = null
)