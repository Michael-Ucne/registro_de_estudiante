package com.phantomshard.estudiantes.di

import android.content.Context
import androidx.room.Room
import com.phantomshard.estudiantes.data.estudiantes.local.TaskDao
import com.phantomshard.estudiantes.data.estudiantes.repository.TaskRepositoryImpl
import com.phantomshard.estudiantes.data.local.database.EstudiantesDb
import com.phantomshard.estudiantes.domain.repository.TaskRepository
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
    fun provideTaskDao(estudiantesDb: EstudiantesDb) = estudiantesDb.taskDao()

    @Provides
    @Singleton
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository = TaskRepositoryImpl(taskDao)
}
