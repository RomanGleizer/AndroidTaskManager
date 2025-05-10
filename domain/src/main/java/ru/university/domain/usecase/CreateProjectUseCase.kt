package ru.university.domain.usecase

import ru.university.domain.model.Project
import ru.university.domain.repository.ProjectRepository
import javax.inject.Inject

class CreateProjectUseCase @Inject constructor(
    private val repository: ProjectRepository
) {
    suspend operator fun invoke(title: String, description: String?): Project =
        repository.createProject(title, description)
}