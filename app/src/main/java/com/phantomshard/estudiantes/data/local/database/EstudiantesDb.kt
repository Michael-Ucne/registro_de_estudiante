package com.phantomshard.estudiantes.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.phantomshard.estudiantes.data.estudiantes.local.EstudianteDao
import com.phantomshard.estudiantes.data.estudiantes.local.EstudianteEntity
import com.phantomshard.estudiantes.data.asignaturas.local.AsignaturaDao
import com.phantomshard.estudiantes.data.asignaturas.local.AsignaturaEntity

@Database(
    entities = [
        EstudianteEntity::class,
        AsignaturaEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class EstudiantesDb : RoomDatabase() {
    abstract fun estudianteDao(): EstudianteDao
    abstract fun asignaturaDao(): AsignaturaDao
}
