// data/src/main/java/ru/university/data/di/RepositoryModule.kt
package ru.university.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import ru.university.data.repository.ProjectRepositoryImpl
import ru.university.data.repository.TaskRepositoryImpl
import ru.university.data.repository.UserRepositoryImpl
import ru.university.domain.repository.ProjectRepository
import ru.university.domain.repository.TaskRepository
import ru.university.domain.repository.UserRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds @Singleton
    abstract fun bindProjectRepository(
        impl: ProjectRepositoryImpl
    ): ProjectRepository

    @Binds @Singleton
    abstract fun bindTaskRepository(
        impl: TaskRepositoryImpl
    ): TaskRepository

    @Binds @Singleton
    abstract fun bindUserRepository(
        impl: UserRepositoryImpl
    ): UserRepository
}
