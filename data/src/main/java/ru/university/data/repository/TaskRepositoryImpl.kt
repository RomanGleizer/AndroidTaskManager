package ru.university.data.repository

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import ru.university.data.dao.TaskDao
import ru.university.data.mapper.toDomain
import ru.university.data.mapper.toEntity
import ru.university.data.model.TaskEntity
import ru.university.domain.model.Task
import ru.university.domain.model.TaskStatus
import ru.university.domain.repository.TaskRepository
import ru.university.network.api.TasksApi
import ru.university.network.model.CreateTaskRequestDto
import ru.university.network.model.UpdateStatusRequestDto
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
        dao.getById(id)?.let { return it.toDomain() }

        val all = dao.getAll()
        val found = all.find { entity -> entity.id == id }
            ?: return null

        val dto = api.getById(found.projectId, id)
        val entity = dto.toEntity()
        dao.insert(entity)
        return entity.toDomain()
    }

    override suspend fun createTask(
        projectId: String,
        title: String,
        description: String?,
        assignedTo: String,
        dueDate: JLocalDateTime?
    ): Task {
        val now  = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val dueK = dueDate?.toKotlinLocalDateTime()

        val newEntity = TaskEntity(
            id         = UUID.randomUUID().toString(),
            projectId  = projectId,
            title      = title,
            description= description,
            assignedTo = assignedTo,
            status     = TaskStatus.TODO.name,
            createdAt  = now,
            dueDate    = dueK
        )
        dao.insert(newEntity)

        val request = CreateTaskRequestDto(
            title        = title,
            description  = description,
            assignedToId = assignedTo,
            dueDate      = dueK?.toString()
        )
        val createdDto = api.create(projectId, request)

        val entity = createdDto.toEntity()
        dao.insert(entity)
        return entity.toDomain()
    }

    override suspend fun updateTaskStatus(taskId: String, status: TaskStatus): Task {
        val existing = dao.getById(taskId)
            ?: throw IllegalStateException("Task $taskId not found locally")
        val projectId = existing.projectId

        val updatedDto = api.updateStatus(
            projectId,
            taskId,
            UpdateStatusRequestDto(status.name)
        )

        val entity = updatedDto.toEntity()
        dao.insert(entity)
        return entity.toDomain()
    }
}
