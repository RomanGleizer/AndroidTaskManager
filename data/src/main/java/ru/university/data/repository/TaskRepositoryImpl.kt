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
import ru.university.network.model.CreateTaskDto
import ru.university.network.model.UpdateStatusDto
import javax.inject.Inject
import java.util.UUID
import java.time.LocalDateTime as JLocalDateTime

class TaskRepositoryImpl @Inject constructor(
    private val dao: TaskDao,
    private val api: TasksApi
) : TaskRepository {

    override suspend fun getTasksForProject(projectId: String): List<Task> {
        val remoteDtos = api.getTasks(projectId)
        val remoteEntities = remoteDtos.map { it.toEntity() }
        remoteEntities.forEach { dao.insert(it) }
        return dao.getForProject(projectId).map { it.toDomain() }
    }

    override suspend fun getTaskById(id: String): Task {
        dao.getById(id)?.let { return it.toDomain() }

        val all = dao.getForProject("") // <-- нужно получить проектId для правильного вызова
        val found = all.find { it.id == id }
            ?: throw IllegalStateException("Task not found locally or remotely")

        val dto = api.getTaskById(found.projectId, id)
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
    ) {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val dueK = dueDate?.toKotlinLocalDateTime()

        val tempEntity = TaskEntity(
            id = UUID.randomUUID().toString(),
            projectId = projectId,
            title = title,
            description = description,
            assignedTo = assignedTo,
            status = TaskStatus.TODO.name,
            createdAt = now,
            dueDate = dueK
        )
        dao.insert(tempEntity)

        val request = CreateTaskDto(
            title = title,
            description = description,
            assignedToId = assignedTo,
            dueDate = dueK?.toString()
        )
        val createdDto = api.createTask(projectId, request)
        dao.insert(createdDto.toEntity())
    }

    override suspend fun updateTaskStatus(taskId: String, status: TaskStatus) {
        val existing = dao.getById(taskId)
            ?: throw IllegalStateException("Task $taskId not found locally")
        val projectId = existing.projectId

        val updatedDto = api.updateTaskStatus(
            projectId,
            taskId,
            UpdateStatusDto(status.name)
        )
        dao.insert(updatedDto.toEntity())
    }
}
