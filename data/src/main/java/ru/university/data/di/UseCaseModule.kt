package ru.university.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.university.domain.repository.ProjectRepository
import ru.university.domain.repository.TaskRepository
import ru.university.domain.repository.UserRepository
import ru.university.domain.usecase.AddMemberUseCase
import ru.university.domain.usecase.CreateProjectUseCase
import ru.university.domain.usecase.CreateTaskUseCase
import ru.university.domain.usecase.GetProjectUseCase
import ru.university.domain.usecase.GetTaskUseCase
import ru.university.domain.usecase.GetTasksUseCase
import ru.university.domain.usecase.GetProfileUseCase
import ru.university.domain.usecase.GetProjectUsersUseCase
import ru.university.domain.usecase.GetProjectsUseCase
import ru.university.domain.usecase.SignInUseCase
import ru.university.domain.usecase.SignOutUseCase
import ru.university.domain.usecase.SignUpUseCase
import ru.university.domain.usecase.UpdateTaskStatusUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetProjectsUseCase(
        projectRepository: ProjectRepository
    ): GetProjectsUseCase =
        GetProjectsUseCase(projectRepository)

    @Provides
    @Singleton
    fun provideCreateProjectUseCase(
        projectRepository: ProjectRepository
    ): CreateProjectUseCase =
        CreateProjectUseCase(projectRepository)

    @Provides
    @Singleton
    fun provideAddMemberUseCase(
        projectRepository: ProjectRepository
    ): AddMemberUseCase =
        AddMemberUseCase(projectRepository)

    @Provides
    @Singleton
    fun provideGetProjectUseCase(
        projectRepository: ProjectRepository
    ): GetProjectUseCase =
        GetProjectUseCase(projectRepository)

    @Provides
    @Singleton
    fun provideGetTasksUseCase(
        taskRepository: TaskRepository
    ): GetTasksUseCase =
        GetTasksUseCase(taskRepository)

    @Provides
    @Singleton
    fun provideGetTaskUseCase(
        taskRepository: TaskRepository
    ): GetTaskUseCase =
        GetTaskUseCase(taskRepository)

    @Provides
    @Singleton
    fun provideCreateTaskUseCase(
        taskRepository: TaskRepository
    ): CreateTaskUseCase =
        CreateTaskUseCase(taskRepository)

    @Provides
    @Singleton
    fun provideUpdateTaskStatusUseCase(
        taskRepository: TaskRepository
    ): UpdateTaskStatusUseCase =
        UpdateTaskStatusUseCase(taskRepository)

    @Provides
    @Singleton
    fun provideSignUpUseCase(
        userRepository: UserRepository
    ): SignUpUseCase =
        SignUpUseCase(userRepository)

    @Provides
    @Singleton
    fun provideSignInUseCase(
        userRepository: UserRepository
    ): SignInUseCase =
        SignInUseCase(userRepository)

    @Provides
    @Singleton
    fun provideSignOutUseCase(
        userRepository: UserRepository
    ): SignOutUseCase =
        SignOutUseCase(userRepository)

    @Provides
    @Singleton
    fun provideGetProfileUseCase(
        userRepository: UserRepository
    ): GetProfileUseCase =
        GetProfileUseCase(userRepository)

    @Provides
    @Singleton
    fun provideGetProjectUsersUseCase(
        projectRepository: ProjectRepository
    ): GetProjectUsersUseCase = GetProjectUsersUseCase(projectRepository)
}
