package ru.university.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.university.data.model.ProjectEntity

@Dao
interface ProjectDao {
    @Query("SELECT * FROM projects")
    suspend fun getAll(): List<ProjectEntity>

    @Query("SELECT * FROM projects WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): ProjectEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(project: ProjectEntity)

    @Query("SELECT lastUpdated FROM projects WHERE id = :id")
    suspend fun getLastUpdatedById(id: String): Long?

    @Query("SELECT MAX(lastUpdated) FROM projects")
    suspend fun getLastUpdated(): Long?

    @Query("DELETE FROM projects")
    suspend fun clearAll()
}
