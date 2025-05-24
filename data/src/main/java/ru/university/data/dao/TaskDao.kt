package ru.university.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.university.data.model.TaskEntity

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks WHERE projectId = :projectId")
    suspend fun getForProject(projectId: String): List<TaskEntity>

    @Query("SELECT * FROM tasks WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): TaskEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: TaskEntity)

    @Query("SELECT lastUpdated FROM projects WHERE id = :id")
    suspend fun getLastUpdatedById(id: String): Long?

    @Query("SELECT MAX(lastUpdated) FROM tasks WHERE projectId = :projectId")
    suspend fun getLastUpdatedForProject(projectId: String): Long?
}
