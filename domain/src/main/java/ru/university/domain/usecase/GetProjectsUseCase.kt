package ru.university.domain.usecase

import ru.university.domain.model.Project
import ru.university.domain.repository.ProjectRepository

class GetProjectsUseCase(private val repository: ProjectRepository) {
    suspend operator fun invoke(): List<Project> = repository.getProjects()
}
