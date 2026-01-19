package com.phantomshard.estudiantes.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.phantomshard.estudiantes.data.estudiantes.local.TaskDao
import com.phantomshard.estudiantes.data.estudiantes.local.TaskEntity

@Database(
    entities = [
        TaskEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class EstudiantesDb : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
