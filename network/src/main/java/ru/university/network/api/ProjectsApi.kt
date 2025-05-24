package ru.university.network.api

import retrofit2.http.*
import ru.university.network.model.*

interface ProjectsApi {
    @GET("projects")
    suspend fun getProjects(): List<ProjectDto>

    @GET("projects/{id}")
    suspend fun getProjectById(@Path("id") projectId: String): ProjectDto

    @POST("projects")
    suspend fun createProject(@Body dto: CreateProjectDto): ProjectDto

    @POST("projects/{id}/members")
    suspend fun addMember(@Path("id") projectId: String, @Body dto: AddMemberDto)

    @GET("projects/{id}/users")
    suspend fun getProjectUsers(@Path("id") projectId: String): List<UserDto>
}
