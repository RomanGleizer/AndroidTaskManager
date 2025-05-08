package ru.university.domain.repository

import ru.university.domain.model.Task
import ru.university.domain.model.TaskStatus
import java.time.LocalDateTime

interface TaskRepository {
    suspend fun getTasksForProject(projectId: String): List<Task>
    suspend fun getTaskById(id: String): Task?
    suspend fun createTask(
        projectId: String,
        title: String,
        description: String?,
        assignedTo: String,
        dueDate: LocalDateTime?
    ): Task
    suspend fun updateTaskStatus(taskId: String, status: TaskStatus): Task
}