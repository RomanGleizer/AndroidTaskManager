package ru.university.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime

@Entity(tableName = "projects")
data class ProjectEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String?,
    val ownerId: String,
    val members: List<String> = emptyList(),
    val createdAt: LocalDateTime,
    val lastUpdated: Long = System.currentTimeMillis()
)