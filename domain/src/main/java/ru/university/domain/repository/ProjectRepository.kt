package ru.university.domain.repository

import ru.university.domain.model.Project

interface ProjectRepository {
    suspend fun getProjects(): List<Project>
    suspend fun createProject(title: String, description: String?)
    suspend fun addMember(projectId: String, inviteId: String)
    suspend fun getProjectById(projectId: String): Project
}
