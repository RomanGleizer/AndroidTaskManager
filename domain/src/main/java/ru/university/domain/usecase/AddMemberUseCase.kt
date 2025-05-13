package ru.university.domain.usecase

import ru.university.domain.repository.ProjectRepository
import javax.inject.Inject

class AddMemberUseCase @Inject constructor(
    private val repository: ProjectRepository
) {
    suspend operator fun invoke(projectId: String, userId: String) =
        repository.addMember(projectId, userId)
}