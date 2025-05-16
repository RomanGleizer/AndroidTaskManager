package ru.university.network.api

import retrofit2.http.*
import ru.university.network.model.CreateTaskRequestDto
import ru.university.network.model.TaskDto
import ru.university.network.model.UpdateStatusRequestDto

interface TasksApi {
    @GET("projects/{projectId}/tasks")
    suspend fun getForProject(
        @Path("projectId") projectId: String
    ): List<TaskDto>

    @GET("projects/{projectId}/tasks/{taskId}")
    suspend fun getById(
        @Path("projectId") projectId: String,
        @Path("taskId")    taskId: String
    ): TaskDto

    @POST("projects/{projectId}/tasks")
    suspend fun create(
        @Path("projectId") projectId: String,
        @Body request: CreateTaskRequestDto
    ): TaskDto

    @PATCH("projects/{projectId}/tasks/{taskId}/status")
    suspend fun updateStatus(
        @Path("projectId") projectId: String,
        @Path("taskId")    taskId: String,
        @Body request: UpdateStatusRequestDto
    ): TaskDto
}
