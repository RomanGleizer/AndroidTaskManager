package ru.university.domain.usecase

import ru.university.domain.repository.TaskRepository
import kotlinx.datetime.toJavaLocalDateTime
import java.time.LocalDateTime

class CreateTaskUseCase(private val repository: TaskRepository) {
    suspend operator fun invoke(
        projectId: String,
        title: String,
        description: String?,
        assignedTo: String,
        dueDate: LocalDateTime?
    ) {
        repository.createTask(projectId, title, description, assignedTo, dueDate)
    }
}
