package ru.university.domain.usecase

import ru.university.domain.model.TaskStatus
import ru.university.domain.repository.TaskRepository

class UpdateTaskStatusUseCase(private val repository: TaskRepository) {
    suspend operator fun invoke(taskId: String, status: TaskStatus) {
        repository.updateTaskStatus(taskId, status)
    }
}
