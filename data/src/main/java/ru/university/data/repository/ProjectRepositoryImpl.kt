package ru.university.data.repository

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.university.data.dao.ProjectDao
import ru.university.data.mapper.toDomain
import ru.university.data.mapper.toDto
import ru.university.data.mapper.toEntity
import ru.university.data.model.ProjectEntity
import ru.university.domain.model.Project
import ru.university.domain.repository.ProjectRepository
import ru.university.network.api.ProjectsApi
import ru.university.network.model.AddMemberRequestDto
import ru.university.network.model.CreateProjectRequestDto
import java.util.UUID
import javax.inject.Inject

class ProjectRepositoryImpl @Inject constructor(
    private val dao: ProjectDao,
    private val api: ProjectsApi
) : ProjectRepository {
    override suspend fun getAllProjects(): List<Project> {
        val remote = api.getAll().map { it.toEntity() }
        remote.forEach { dao.insert(it) }
        return dao.getAll().map { it.toDomain() }
    }

    override suspend fun getProjectById(id: String): Project? {
        val local = dao.getById(id)
        return local?.toDomain()
            ?: api.getById(id).toEntity().also { dao.insert(it) }.toDomain()
    }

    override suspend fun createProject(title: String, description: String?): Project {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val entity = ProjectEntity(
            id = UUID.randomUUID().toString(),
            title = title,
            description = description,
            ownerId = "",
            members = emptyList(),
            createdAt = now
        )
        dao.insert(entity)
        val dto = api.create(CreateProjectRequestDto(title, description))
        val saved = dto.toEntity()
        dao.insert(saved)
        return saved.toDomain()
    }

    override suspend fun addMember(projectId: String, userId: String) {
        api.addMember(
            projectId,
            AddMemberRequestDto(userId)
        )

        dao.getById(projectId)
            ?.let { existing ->
                val updated = existing.copy(
                    members = existing.members + userId
                )
                dao.insert(updated)
            }
    }
}
