package ru.university.domain.usecase

import ru.university.domain.model.Task
import ru.university.domain.repository.TaskRepository
import javax.inject.Inject

class GetTaskByIdUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(projectId: String, taskId: String): Task {
        return taskRepository.getTaskById(projectId, taskId)
    }
}
