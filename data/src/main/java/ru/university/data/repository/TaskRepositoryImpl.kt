package ru.university.data.repository

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import ru.university.data.dao.TaskDao
import ru.university.data.mapper.toDomain
import ru.university.data.mapper.toDto
import ru.university.data.mapper.toEntity
import ru.university.data.model.TaskEntity
import ru.university.domain.model.Task
import ru.university.domain.model.TaskStatus
import ru.university.domain.repository.TaskRepository
import ru.university.network.api.TasksApi
import java.time.LocalDateTime as JLocalDateTime
import java.util.UUID
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val dao: TaskDao,
    private val api: TasksApi
) : TaskRepository {
    override suspend fun getTasksForProject(projectId: String): List<Task> {
        val remote = api.getForProject(projectId).map { it.toEntity() }
        remote.forEach { dao.insert(it) }
        return dao.getForProject(projectId).map { it.toDomain() }
    }

    override suspend fun getTaskById(id: String): Task? {
        val local = dao.getById(id)
        return local?.toDomain()
            ?: api.getById(id).toEntity().also { dao.insert(it) }.toDomain()
    }

    override suspend fun createTask(
        projectId: String,
        title: String,
        description: String?,
        assignedTo: String,
        dueDate: JLocalDateTime?
    ): Task {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val dueK: kotlinx.datetime.LocalDateTime? = dueDate?.toKotlinLocalDateTime()

        val newEntity = TaskEntity(
            id = UUID.randomUUID().toString(),
            projectId = projectId,
            title = title,
            description = description,
            assignedTo = assignedTo,
            status = TaskStatus.TODO.name,
            createdAt = now,
            dueDate = dueK
        )
        val createdDto = api.create(projectId, newEntity.toDto())
        val entity = createdDto.toEntity()
        dao.insert(entity)
        return entity.toDomain()
    }

    override suspend fun updateTaskStatus(taskId: String, status: TaskStatus): Task {
        val updatedDto = api.updateStatus(taskId, mapOf("status" to status.name))
        val entity = updatedDto.toEntity()
        dao.insert(entity)
        return entity.toDomain()
    }
}
