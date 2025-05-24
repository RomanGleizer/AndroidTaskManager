package ru.university.domain.usecase

import ru.university.domain.model.User
import ru.university.domain.repository.ProjectRepository
import javax.inject.Inject

class GetProjectUsersUseCase @Inject constructor(
    private val repository: ProjectRepository
) {
    suspend operator fun invoke(projectId: String): List<User> = repository.getProjectUsers(projectId)
}
