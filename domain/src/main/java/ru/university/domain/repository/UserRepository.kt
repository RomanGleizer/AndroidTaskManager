package ru.university.domain.repository

import ru.university.domain.model.User

interface UserRepository {
    suspend fun getCurrentUser(): User?
    suspend fun signIn(email: String, password: String): User
    suspend fun signUp(name: String, email: String, password: String): User
    suspend fun signOut()
}