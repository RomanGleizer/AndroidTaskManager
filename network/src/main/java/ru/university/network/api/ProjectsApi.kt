package ru.university.network.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.university.network.model.ProjectDto

interface ProjectsApi {
    @GET("/projects")
    suspend fun getAll(): List<ProjectDto>

    @GET("/projects/{id}")
    suspend fun getById(@Path("id") id: String): ProjectDto

    @POST("/projects")
    suspend fun create(@Body project: ProjectDto): ProjectDto

    @POST("/projects/{id}/members/{userId}")
    suspend fun addMember(
        @Path("id") projectId: String,
        @Path("userId") userId: String
    )
}
