package ru.university.data.mapper

import ru.university.data.model.ProjectEntity
import ru.university.data.model.TaskEntity
import ru.university.domain.model.Project
import ru.university.domain.model.Task
import ru.university.domain.model.TaskStatus
import ru.university.network.model.ProjectDto
import ru.university.network.model.TaskDto
import kotlinx.datetime.toJavaLocalDateTime

fun ProjectDto.toEntity(): ProjectEntity = ProjectEntity(
    id = id,
    title = title,
    description = description,
    ownerId = ownerId,
    members = members,
    createdAt = createdAt
)

fun ProjectEntity.toDomain(): Project = Project(
    id = id,
    title = title,
    description = description,
    ownerId = ownerId,
    members = members,
    createdAt = createdAt.toJavaLocalDateTime()
)

fun ProjectEntity.toDto(): ProjectDto = ProjectDto(
    id = id,
    title = title,
    description = description,
    ownerId = ownerId,
    members = members,
    createdAt = createdAt
)

fun TaskDto.toEntity(): TaskEntity = TaskEntity(
    id = id,
    projectId = projectId,
    title = title,
    description = description,
    assignedTo = assignedTo,
    status = status,
    createdAt = createdAt,
    dueDate = dueDate
)

fun TaskEntity.toDomain(): Task = Task(
    id = id,
    projectId = projectId,
    title = title,
    description = description,
    assignedTo = assignedTo,
    status = TaskStatus.valueOf(status),
    createdAt = createdAt.toJavaLocalDateTime(),
    dueDate = dueDate?.toJavaLocalDateTime()
)

fun TaskEntity.toDto(): TaskDto = TaskDto(
    id = id,
    projectId = projectId,
    title = title,
    description = description,
    assignedTo = assignedTo,
    status = status,
    createdAt = createdAt,
    dueDate = dueDate
)

fun TaskDto.toDomain(): Task = Task(
    id = id,
    projectId = projectId,
    title = title,
    description = description,
    assignedTo = assignedTo,
    status = TaskStatus.valueOf(status),
    createdAt = createdAt.toJavaLocalDateTime(),
    dueDate = dueDate?.toJavaLocalDateTime()
)