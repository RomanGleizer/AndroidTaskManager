package ru.university.network.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import ru.university.network.model.TaskDto

interface TasksApi {
    @GET("/projects/{projectId}/tasks")
    suspend fun getForProject(@Path("projectId") projectId: String): List<TaskDto>

    @GET("/tasks/{id}")
    suspend fun getById(@Path("id") id: String): TaskDto

    @POST("/projects/{projectId}/tasks")
    suspend fun create(
        @Path("projectId") projectId: String,
        @Body task: TaskDto
    ): TaskDto

    @PUT("/tasks/{id}/status")
    suspend fun updateStatus(
        @Path("id") taskId: String,
        @Body statusWrapper: Map<String, String>
    ): TaskDto
}