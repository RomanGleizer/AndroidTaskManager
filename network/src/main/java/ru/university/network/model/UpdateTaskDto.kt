package ru.university.network.model

data class UpdateTaskDto(
    val title: String,
    val description: String?,
    val assignedToId: String,
    val dueDate: String?,
    val status: String
)
