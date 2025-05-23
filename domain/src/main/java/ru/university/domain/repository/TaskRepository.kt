package ru.university.domain.repository

import ru.university.domain.model.Task
import ru.university.domain.model.TaskStatus
import java.time.LocalDateTime

interface TaskRepository {
    suspend fun getTasksForProject(projectId: String): List<Task>
    suspend fun getTaskById(projectId: String, id: String): Task
    suspend fun createTask(
        projectId: String,
        title: String,
        description: String?,
        assignedTo: String,
        dueDate: LocalDateTime?
    )

    suspend fun updateTaskStatus(taskId: String, status: ru.university.domain.model.TaskStatus)

    suspend fun updateTask(
        projectId: String,
        taskId: String,
        title: String,
        description: String?,
        assignedTo: String,
        dueDate: java.time.LocalDateTime?,
        status: TaskStatus
    )
}
