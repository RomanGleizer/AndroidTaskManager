package ru.university.data.mapper

import ru.university.data.db.Converters.parseToLocalDateTime
import ru.university.data.db.Converters.parseToLocalDateTimeOrDefault
import ru.university.data.db.Converters.parseToLocalDateTimeOrNull
import ru.university.domain.model.*
import ru.university.network.model.*
import ru.university.data.model.*

fun UserDto.toDomain(): User = User(id, name, email, inviteId)

fun ProjectDto.toDomain(): Project = Project(
    id = id,
    title = title,
    description = description,
    ownerId = ownerId,
    members = members,
    createdAt = createdAt.parseToLocalDateTime()
)

fun ProjectDto.toEntity(): ProjectEntity = ProjectEntity(
    id = id,
    title = title,
    description = description,
    ownerId = ownerId,
    members = members ?: emptyList(),
    createdAt = createdAt.parseToLocalDateTime()
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
    id = this.id,
    projectId = this.projectId,
    title = this.title,
    description = this.description,
    status = TaskStatus.valueOf(this.status),
    createdAt = createdAt.parseToLocalDateTimeOrDefault(),
    dueDate = dueDate?.parseToLocalDateTimeOrNull(),
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
    createdAt = createdAt.parseToLocalDateTimeOrDefault(),
    dueDate = dueDate?.parseToLocalDateTimeOrNull()
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

fun TaskEntity.toDto(): TaskDto = TaskDto(
    id = id,
    projectId = projectId,
    title = title,
    description = description,
    status = status,
    createdAt = createdAt.toString(),
    dueDate = dueDate?.toString(),
    assignedToId = assignedTo,
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
