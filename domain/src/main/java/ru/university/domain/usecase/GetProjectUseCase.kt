package ru.university.domain.usecase

import ru.university.domain.model.Project
import ru.university.domain.repository.ProjectRepository

class GetProjectUseCase(private val repository: ProjectRepository) {
    suspend operator fun invoke(projectId: String): Project = repository.getProjectById(projectId)
}
