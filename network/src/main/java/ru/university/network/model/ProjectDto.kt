package ru.university.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDateTime

@Serializable
data class ProjectDto(
    val id: String,
    val title: String,
    val description: String? = null,
    @SerialName("owner_id") val ownerId: String,
    val members: List<String>,
    @SerialName("created_at") val createdAt: LocalDateTime
)