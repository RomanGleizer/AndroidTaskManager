package ru.university.domain.usecase

import ru.university.domain.model.User
import ru.university.domain.repository.UserRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val repo: UserRepository
) {
    suspend operator fun invoke(): User? = repo.getCurrentUser()
}