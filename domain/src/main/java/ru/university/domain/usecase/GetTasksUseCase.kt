package ru.university.domain.usecase

import ru.university.domain.model.Task
import ru.university.domain.repository.TaskRepository
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(projectId: String): List<Task> =
        repository.getTasksForProject(projectId)
}