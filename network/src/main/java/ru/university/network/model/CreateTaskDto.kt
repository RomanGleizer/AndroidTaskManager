package ru.university.network.model

data class CreateTaskDto(
    val title: String,
    val description: String?,
    val assignedToId: String,
    val dueDate: String?
)
