package com.phantomshard.estudiantes.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.phantomshard.estudiantes.data.estudiantes.local.TaskDao
import com.phantomshard.estudiantes.data.estudiantes.local.TaskEntity
import com.phantomshard.estudiantes.data.asignaturas.local.AsignaturaDao
import com.phantomshard.estudiantes.data.asignaturas.local.AsignaturaEntity

@Database(
    entities = [
        TaskEntity::class,
        AsignaturaEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class EstudiantesDb : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun asignaturaDao(): AsignaturaDao
}
