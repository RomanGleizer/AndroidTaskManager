package ru.university.network.model

data class CreateProjectRequestDto(
    val title: String,
    val description: String?
)