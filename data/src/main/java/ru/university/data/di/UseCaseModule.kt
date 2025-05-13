package ru.university.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.university.domain.repository.ProjectRepository
import ru.university.domain.repository.TaskRepository
import ru.university.domain.repository.UserRepository
import ru.university.domain.usecase.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideSignInUseCase(userRepository: UserRepository) = SignInUseCase(userRepository)

    @Provides
    @Singleton
    fun provideSignUpUseCase(userRepository: UserRepository) = SignUpUseCase(userRepository)

    @Provides
    @Singleton
    fun provideGetProjectsUseCase(repo: ProjectRepository) = GetProjectsUseCase(repo)

    @Provides
    @Singleton
    fun provideCreateProjectUseCase(repo: ProjectRepository) = CreateProjectUseCase(repo)

    @Provides
    @Singleton
    fun provideAddMemberUseCase(repo: ProjectRepository) = AddMemberUseCase(repo)

    @Provides
    @Singleton
    fun provideGetTasksUseCase(repo: TaskRepository) = GetTasksUseCase(repo)

    @Provides
    @Singleton
    fun provideGetTaskUseCase(repo: TaskRepository) = GetTaskUseCase(repo)

    @Provides
    @Singleton
    fun provideCreateTaskUseCase(repo: TaskRepository) = CreateTaskUseCase(repo)

    @Provides
    @Singleton
    fun provideUpdateTaskStatusUseCase(repo: TaskRepository) = UpdateTaskStatusUseCase(repo)
}
