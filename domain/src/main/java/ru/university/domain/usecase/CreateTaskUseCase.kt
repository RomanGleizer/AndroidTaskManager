package ru.university.domain.usecase

import ru.university.domain.model.Task
import ru.university.domain.repository.TaskRepository
import java.time.LocalDateTime
import javax.inject.Inject

class CreateTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(
        projectId: String,
        title: String,
        description: String?,
        assignedTo: String,
        dueDate: LocalDateTime?
    ): Task = repository.createTask(projectId, title, description, assignedTo, dueDate)
}