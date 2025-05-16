package ru.university.network.model

import kotlinx.serialization.Serializable

@Serializable
data class UpdateStatusRequestDto(
    val status: String
)