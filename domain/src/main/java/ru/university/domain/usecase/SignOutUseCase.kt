package ru.university.domain.usecase

import ru.university.domain.repository.UserRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val repo: UserRepository
) {
    suspend operator fun invoke() {
        repo.signOut()
    }
}