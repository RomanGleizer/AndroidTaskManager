package ru.university.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.university.data.dao.ProjectDao
import ru.university.data.dao.TaskDao
import ru.university.data.model.ProjectEntity
import ru.university.data.model.TaskEntity

@Database(
    entities = [ProjectEntity::class, TaskEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun projectDao(): ProjectDao
    abstract fun taskDao(): TaskDao
}
