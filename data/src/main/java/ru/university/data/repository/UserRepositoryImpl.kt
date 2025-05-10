package ru.university.data.repository

import kotlinx.coroutines.flow.first
import javax.inject.Inject
import ru.university.domain.model.User
import ru.university.domain.repository.UserRepository
import ru.university.network.api.AuthApi
import ru.university.data.preference.UserPreferencesDataStore
import ru.university.network.model.AuthRequestDto

class UserRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val prefs: UserPreferencesDataStore
) : UserRepository {

    override suspend fun getCurrentUser(): User? {
        val token = prefs.authTokenFlow.first() ?: return null
        return null
    }

    override suspend fun signIn(email: String, password: String): User {
        val resp = api.signIn(AuthRequestDto(email, password))
        prefs.saveAuthToken(resp.token)
        return User(id = resp.userId, name = resp.name, email = resp.email)
    }

    override suspend fun signUp(name: String, email: String, password: String): User {
        val resp = api.signUp(AuthRequestDto(email, password))
        prefs.saveAuthToken(resp.token)
        return User(id = resp.userId, name = resp.name, email = resp.email)
    }

    override suspend fun signOut() {
        prefs.clearAuthToken()
    }
}