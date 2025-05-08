package ru.university.domain.repository

import ru.university.domain.model.Project

interface ProjectRepository {
    suspend fun getAllProjects(): List<Project>
    suspend fun getProjectById(id: String): Project?
    suspend fun createProject(title: String, description: String?): Project
    suspend fun addMember(projectId: String, userId: String)
}
