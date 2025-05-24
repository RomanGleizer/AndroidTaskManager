package ru.university.domain.usecase

import ru.university.domain.repository.ProjectRepository

class CreateProjectUseCase(private val repository: ProjectRepository) {
    suspend operator fun invoke(title: String, description: String?) {
        repository.createProject(title, description)
    }
}
