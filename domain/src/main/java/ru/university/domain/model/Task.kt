package ru.university.domain.model

import kotlinx.datetime.LocalDateTime

data class Task(
    val id: String,
    val projectId: String,
    val title: String,
    val description: String?,
    val status: TaskStatus,
    val createdAt: LocalDateTime,
    val dueDate: LocalDateTime?,
    val assignedTo: String,
    val assignedToName: String
)