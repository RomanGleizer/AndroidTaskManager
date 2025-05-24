package ru.university.domain.usecase

import ru.university.domain.model.TaskStatus
import ru.university.domain.repository.TaskRepository
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(
        projectId: String,
        taskId: String,
        title: String,
        description: String,
        assignedTo: String,
        dueDate: java.time.LocalDateTime?,
        status: TaskStatus
    ) {
        taskRepository.updateTask(
            projectId,
            taskId,
            title,
            description,
            assignedTo,
            dueDate,
            status
        )
    }
}
