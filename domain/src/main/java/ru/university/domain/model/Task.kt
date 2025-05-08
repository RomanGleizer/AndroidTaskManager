package ru.university.domain.model

import java.time.LocalDateTime

data class Task(
    val id: String,
    val projectId: String,
    val title: String,
    val description: String?,
    val assignedTo: String,
    val status: TaskStatus,
    val createdAt: LocalDateTime,
    val dueDate: LocalDateTime?
)
