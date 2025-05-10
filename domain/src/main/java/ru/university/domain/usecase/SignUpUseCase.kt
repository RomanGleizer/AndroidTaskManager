package ru.university.domain.usecase

import ru.university.domain.model.User
import ru.university.domain.repository.UserRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(name: String, email: String, password: String): User =
        userRepository.signUp(name, email, password)
}