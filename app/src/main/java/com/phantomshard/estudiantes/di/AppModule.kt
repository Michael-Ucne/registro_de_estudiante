package com.phantomshard.estudiantes.di

import android.content.Context
import androidx.room.Room
import com.phantomshard.estudiantes.data.estudiantes.local.EstudianteDao
import com.phantomshard.estudiantes.data.estudiantes.repository.EstudianteRepositoryImpl
import com.phantomshard.estudiantes.data.asignaturas.local.AsignaturaDao
import com.phantomshard.estudiantes.data.asignaturas.repository.AsignaturaRepositoryImpl
import com.phantomshard.estudiantes.data.local.database.EstudiantesDb
import com.phantomshard.estudiantes.domain.repository.EstudianteRepository
import com.phantomshard.estudiantes.domain.repository.AsignaturaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun provideEstudiantesDb(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            EstudiantesDb::class.java,
            "Estudiantes.db"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideEstudianteDao(estudiantesDb: EstudiantesDb) = estudiantesDb.estudianteDao()

    @Provides
    @Singleton
    fun provideEstudianteRepository(estudianteDao: EstudianteDao): EstudianteRepository = EstudianteRepositoryImpl(estudianteDao)

    @Provides
    @Singleton
    fun provideAsignaturaDao(estudiantesDb: EstudiantesDb) = estudiantesDb.asignaturaDao()

    @Provides
    @Singleton
    fun provideAsignaturaRepository(asignaturaDao: AsignaturaDao): AsignaturaRepository = AsignaturaRepositoryImpl(asignaturaDao)
}
