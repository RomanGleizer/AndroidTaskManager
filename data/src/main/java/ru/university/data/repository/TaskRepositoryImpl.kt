package ru.university.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.datetime.LocalDateTime
import ru.university.data.dao.TaskDao
import ru.university.data.db.Converters.toLocalDateTimeSafe
import ru.university.data.mapper.toDomain
import ru.university.data.mapper.toDto
import ru.university.data.mapper.toEntity
import ru.university.domain.model.Task
import ru.university.domain.model.TaskStatus
import ru.university.domain.repository.TaskRepository
import ru.university.network.api.TasksApi
import ru.university.network.model.CreateTaskDto
import ru.university.network.model.UpdateStatusDto
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val dao: TaskDao,
    private val api: TasksApi
) : TaskRepository {

    private val cacheDurationMs = 5 * 60 * 1000L

    override suspend fun getTasksForProject(projectId: String): List<Task> {
        val now = System.currentTimeMillis()
        val lastUpdate = dao.getLastUpdatedForProject(projectId) ?: 0L

        if (now - lastUpdate > cacheDurationMs) {
            val remoteDtos = api.getTasks(projectId)
            val remoteEntities = remoteDtos.map { dto ->
                dto.toEntity().copy(lastUpdated = now)
            }
            remoteEntities.forEach { dao.insert(it) }
        }

        return dao.getForProject(projectId).map { entity ->
            val dto = entity.toDto()

            Task(
                id = dto.id,
                projectId = dto.projectId,
                title = dto.title,
                description = dto.description,
                status = TaskStatus.valueOf(dto.status),
                createdAt = dto.createdAt.toLocalDateTimeSafe() ?: LocalDateTime(1970,1,1,0,0),
                dueDate = dto.dueDate?.toLocalDateTimeSafe(),
                assignedTo = dto.assignedToId,
                assignedToName = dto.assignedToName
            )
        }
    }

    override suspend fun getTaskById(projectId: String, id: String): Task {
        dao.getById(id)?.let { return it.toDomain() }

        val all = dao.getForProject(projectId)
        val found = all.find { it.id == id }
            ?: throw IllegalStateException("Task not found locally or remotely")

        val dto = api.getTaskById(projectId, id)
        val entity = dto.toEntity().copy(lastUpdated = System.currentTimeMillis())
        dao.insert(entity)
        return entity.toDomain()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun createTask(
        projectId: String,
        title: String,
        description: String?,
        assignedTo: String,
        dueDate: java.time.LocalDateTime?
    ) {
        val dueDateString: String? = dueDate?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

        val createdDto = api.createTask(
            projectId,
            CreateTaskDto(title, description, assignedTo, dueDateString)
        )
        dao.insert(createdDto.toEntity().copy(lastUpdated = System.currentTimeMillis()))
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
        dao.insert(updatedDto.toEntity().copy(lastUpdated = System.currentTimeMillis()))
    }
}
