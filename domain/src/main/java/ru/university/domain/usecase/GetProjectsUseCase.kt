package ru.university.domain.usecase

import ru.university.domain.model.Project
import ru.university.domain.repository.ProjectRepository
import javax.inject.Inject

class GetProjectsUseCase @Inject constructor(
    private val repository: ProjectRepository
) {
    suspend operator fun invoke(): List<Project> =
        repository.getAllProjects()
}
