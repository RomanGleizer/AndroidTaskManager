package ru.university.data.repository

import kotlinx.coroutines.flow.first
import javax.inject.Inject
import ru.university.domain.model.User
import ru.university.domain.repository.UserRepository
import ru.university.network.api.AuthApi
import ru.university.data.preference.UserPreferencesDataStore
import ru.university.network.model.AuthRequestDto
import ru.university.network.model.SignUpRequestDto

class UserRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val prefs: UserPreferencesDataStore
) : UserRepository {

    override suspend fun getCurrentUser(): User {
        val dto = api.getProfile()
        return User(
            id    = dto.id,
            name  = dto.name,
            email = dto.email
        )
    }

    override suspend fun signIn(email: String, password: String): User {
        val resp = api.login(AuthRequestDto(email, password))
        prefs.saveAuthToken(resp.token)
        return User(
            id = resp.userId,
            name = resp.name,
            email = resp.email
        )
    }

    override suspend fun signUp(name: String, email: String, password: String): User {
        val resp = api.signUp(SignUpRequestDto(name, email, password))
        prefs.saveAuthToken(resp.token)
        return User(
            id = resp.userId,
            name = resp.name,
            email = resp.email
        )
    }

    override suspend fun signOut() {
        prefs.clearAuthToken()
    }
}
