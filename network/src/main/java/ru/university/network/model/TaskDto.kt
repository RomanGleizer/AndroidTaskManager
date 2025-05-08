package ru.university.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDateTime

@Serializable
data class TaskDto(
    val id: String,
    @SerialName("project_id") val projectId: String,
    val title: String,
    val description: String? = null,
    @SerialName("assigned_to") val assignedTo: String,
    val status: String,
    @SerialName("created_at") val createdAt: LocalDateTime,
    @SerialName("due_date") val dueDate: LocalDateTime? = null
)