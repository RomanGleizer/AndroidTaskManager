package ru.university.data.repository

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.university.data.dao.ProjectDao
import ru.university.data.mapper.toDomain
import ru.university.data.mapper.toEntity
import ru.university.data.model.ProjectEntity
import ru.university.domain.model.Project
import ru.university.domain.repository.ProjectRepository
import ru.university.network.api.ProjectsApi
import ru.university.network.model.AddMemberDto
import ru.university.network.model.CreateProjectDto
import javax.inject.Inject
import java.util.UUID

class ProjectRepositoryImpl @Inject constructor(
    private val dao: ProjectDao,
    private val api: ProjectsApi
) : ProjectRepository {

    override suspend fun getProjects(): List<Project> {
        val remoteDtos = api.getProjects()
        val remoteEntities = remoteDtos.map { it.toEntity() }
        remoteEntities.forEach { dao.insert(it) }
        return dao.getAll().map { it.toDomain() }
    }

    override suspend fun getProjectById(id: String): Project {
        dao.getById(id)?.let { return it.toDomain() }
        val remoteDto = api.getProjectById(id)
        val entity = remoteDto.toEntity()
        dao.insert(entity)
        return entity.toDomain()
    }

    override suspend fun createProject(title: String, description: String?) {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val tempEntity = ProjectEntity(
            id = UUID.randomUUID().toString(),
            title = title,
            description = description,
            ownerId = "",
            members = emptyList(),
            createdAt = now
        )
        dao.insert(tempEntity)

        val createdDto = api.createProject(CreateProjectDto(title, description))
        dao.insert(createdDto.toEntity())
    }

    override suspend fun addMember(projectId: String, inviteId: String) {
        api.addMember(projectId, AddMemberDto(inviteId))

        dao.getById(projectId)?.let {
            val updated = it.copy(members = it.members + inviteId)
            dao.insert(updated)
        }
    }
}
