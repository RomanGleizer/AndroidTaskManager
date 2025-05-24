package ru.university.network.model

import kotlinx.serialization.Serializable

@Serializable
data class TaskDto(
    val id: String,
    val projectId: String,
    val title: String,
    val description: String?,
    val status: String,
    val createdAt: String,
    val dueDate: String?,
    val assignedToId: String,
    val assignedToName: String
)