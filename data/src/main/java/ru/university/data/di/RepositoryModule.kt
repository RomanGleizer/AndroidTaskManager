package ru.university.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.university.data.repository.ProjectRepositoryImpl
import ru.university.data.repository.TaskRepositoryImpl
import ru.university.data.repository.UserRepositoryImpl
import ru.university.domain.repository.ProjectRepository
import ru.university.domain.repository.TaskRepository
import ru.university.domain.repository.UserRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindProjectRepository(
        impl: ProjectRepositoryImpl
    ): ProjectRepository

    @Binds
    abstract fun bindTaskRepository(
        impl: TaskRepositoryImpl
    ): TaskRepository

    @Binds
    abstract fun bindUserRepository(
        impl: UserRepositoryImpl
    ): UserRepository
}
