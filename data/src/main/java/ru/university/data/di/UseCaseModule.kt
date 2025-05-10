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
    @JvmStatic
    fun provideSignInUseCase(
        userRepository: UserRepository
    ): SignInUseCase = SignInUseCase(userRepository)

    @Provides
    @Singleton
    @JvmStatic
    fun provideSignUpUseCase(
        userRepository: UserRepository
    ): SignUpUseCase = SignUpUseCase(userRepository)

    @Provides
    @Singleton
    @JvmStatic
    fun provideGetProjectsUseCase(
        projectRepository: ProjectRepository
    ): GetProjectsUseCase = GetProjectsUseCase(projectRepository)

    @Provides
    @Singleton
    @JvmStatic
    fun provideCreateProjectUseCase(
        projectRepository: ProjectRepository
    ): CreateProjectUseCase = CreateProjectUseCase(projectRepository)

    @Provides
    @Singleton
    @JvmStatic
    fun provideGetTasksUseCase(
        taskRepository: TaskRepository
    ): GetTasksUseCase = GetTasksUseCase(taskRepository)

    @Provides
    @Singleton
    @JvmStatic
    fun provideGetTaskUseCase(
        taskRepository: TaskRepository
    ): GetTaskUseCase = GetTaskUseCase(taskRepository)

    @Provides
    @Singleton
    @JvmStatic
    fun provideCreateTaskUseCase(
        taskRepository: TaskRepository
    ): CreateTaskUseCase = CreateTaskUseCase(taskRepository)

    @Provides
    @Singleton
    @JvmStatic
    fun provideUpdateTaskStatusUseCase(
        taskRepository: TaskRepository
    ): UpdateTaskStatusUseCase = UpdateTaskStatusUseCase(taskRepository)
}
