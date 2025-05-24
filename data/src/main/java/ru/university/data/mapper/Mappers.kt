package ru.university.data.mapper

import ru.university.domain.model.*
import ru.university.network.model.*
import ru.university.data.model.*
import kotlinx.datetime.LocalDateTime

fun UserDto.toDomain(): User = User(id, name, email, inviteId)

fun ProjectDto.toDomain(): Project = Project(
    id = id,
    title = title,
    description = description,
    ownerId = ownerId,
    members = members,
    createdAt = LocalDateTime.parse(createdAt)
)

fun ProjectDto.toEntity(): ProjectEntity = ProjectEntity(
    id = id,
    title = title,
    description = description,
    ownerId = ownerId,
    members = members ?: emptyList(),
    createdAt = parseLocalDateTime(createdAt)
)

fun ProjectEntity.toDomain(): Project = Project(
    id = id,
    title = title,
    description = description,
    ownerId = ownerId,
    members = members,
    createdAt = createdAt
)

fun TaskDto.toDomain(): Task = Task(
    id = id,
    projectId = projectId,
    title = title,
    description = description,
    status = TaskStatus.valueOf(status),
    createdAt = LocalDateTime.parse(createdAt),
    dueDate = dueDate?.let { LocalDateTime.parse(it) },
    assignedTo = assignedToId,
    assignedToName = assignedToName
)

fun TaskDto.toEntity(): TaskEntity = TaskEntity(
    id = id,
    projectId = projectId,
    title = title,
    description = description,
    assignedTo = assignedToId,
    status = status,
    createdAt = LocalDateTime.parse(createdAt),
    dueDate = dueDate?.let { LocalDateTime.parse(it) }
)

fun TaskEntity.toDomain(): Task = Task(
    id = id,
    projectId = projectId,
    title = title,
    description = description,
    status = TaskStatus.valueOf(status),
    createdAt = createdAt,
    dueDate = dueDate,
    assignedTo = assignedTo,
    assignedToName = ""
)

fun Project.toDto(): CreateProjectDto = CreateProjectDto(title, description)

fun Task.toDto(): CreateTaskDto = CreateTaskDto(
    title = title,
    description = description,
    assignedToId = assignedTo,
    dueDate = dueDate?.toString()
)

fun TaskStatus.toDto(): String = name

fun parseLocalDateTime(input: String): LocalDateTime {
    val cleanInput = if (input.endsWith("Z")) input.removeSuffix("Z") else input
    return LocalDateTime.parse(cleanInput)
}