package ru.university.domain.usecase

import ru.university.domain.repository.ProjectRepository

class AddMemberUseCase(private val repository: ProjectRepository) {
    suspend operator fun invoke(projectId: String, inviteId: String) {
        repository.addMember(projectId, inviteId)
    }
}
