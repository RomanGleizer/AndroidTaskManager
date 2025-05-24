package ru.university.network.api

import retrofit2.http.*
import ru.university.network.model.*

interface TasksApi {
    @GET("projects/{projectId}/tasks")
    suspend fun getTasks(@Path("projectId") projectId: String): List<TaskDto>

    @GET("projects/{projectId}/tasks/{taskId}")
    suspend fun getTaskById(@Path("projectId") projectId: String, @Path("taskId") taskId: String): TaskDto

    @POST("projects/{projectId}/tasks")
    suspend fun createTask(@Path("projectId") projectId: String, @Body dto: CreateTaskDto): TaskDto

    @PATCH("projects/{projectId}/tasks/{taskId}/status")
    suspend fun updateTaskStatus(
        @Path("projectId") projectId: String,
        @Path("taskId") taskId: String,
        @Body dto: UpdateStatusDto
    ): TaskDto

    @PATCH("projects/{projectId}/tasks/{taskId}")
    suspend fun updateTask(
        @Path("projectId") projectId: String,
        @Path("taskId") taskId: String,
        @Body dto: UpdateTaskDto
    ): TaskDto
}
