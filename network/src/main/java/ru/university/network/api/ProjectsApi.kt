package ru.university.network.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.university.network.model.ProjectDto
import ru.university.network.model.CreateProjectRequestDto
import ru.university.network.model.AddMemberRequestDto

interface ProjectsApi {
    @GET("projects")
    suspend fun getAll(): List<ProjectDto>

    @GET("projects/{id}")
    suspend fun getById(@Path("id") id: String): ProjectDto

    @POST("projects")
    suspend fun create(
        @Body request: CreateProjectRequestDto
    ): ProjectDto

    @POST("projects/{id}/members")
    suspend fun addMember(
        @Path("id") projectId: String,
        @Body request: AddMemberRequestDto
    )
}
