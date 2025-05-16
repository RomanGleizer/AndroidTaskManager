package ru.university.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDateTime

@Serializable
data class ProjectDto(
    val id: String,
    val title: String,
    val description: String? = null,
    @SerialName("ownerId")    val ownerId: String,
    @SerialName("memberIds")  val members: List<String> = emptyList(),
    @SerialName("participants")
    val participants: List<UserDto> = emptyList(),
    @SerialName("createdAt")  val createdAt: String
)