package ru.university.domain.usecase

import ru.university.domain.model.User
import ru.university.domain.repository.UserRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(email: String, password: String): User =
        userRepository.signIn(email, password)
}