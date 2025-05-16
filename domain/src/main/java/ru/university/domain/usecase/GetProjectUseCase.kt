package ru.university.domain.usecase

import ru.university.domain.model.Project
import ru.university.domain.repository.ProjectRepository
import javax.inject.Inject

class GetProjectUseCase @Inject constructor(
    private val repository: ProjectRepository
) {
    suspend operator fun invoke(projectId: String): Project =
        repository.getProjectById(projectId)
            ?: throw IllegalStateException("Проект с id=$projectId не найден")
}
