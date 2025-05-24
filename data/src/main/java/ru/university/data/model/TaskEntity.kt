package ru.university.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey val id: String,
    val projectId: String,
    val title: String,
    val description: String?,
    val assignedTo: String,
    val status: String,
    val createdAt: LocalDateTime,
    val dueDate: LocalDateTime?,
    val lastUpdated: Long = System.currentTimeMillis()
)