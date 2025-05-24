package ru.university.domain.usecase

import ru.university.domain.model.Task
import ru.university.domain.repository.TaskRepository

class GetTaskUseCase(private val repository: TaskRepository) {
    suspend operator fun invoke(taskId: String): Task = repository.getTaskById(taskId)
}
