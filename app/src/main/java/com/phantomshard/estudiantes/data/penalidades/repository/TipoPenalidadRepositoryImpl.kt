package com.phantomshard.estudiantes.data.penalidades.repository

import com.phantomshard.estudiantes.data.penalidades.local.TipoPenalidadDao
import com.phantomshard.estudiantes.data.penalidades.local.TipoPenalidadEntity
import com.phantomshard.estudiantes.domain.model.TipoPenalidad
import com.phantomshard.estudiantes.domain.repository.TipoPenalidadRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TipoPenalidadRepositoryImpl(
    private val dao: TipoPenalidadDao
) : TipoPenalidadRepository {
    override suspend fun save(tipo: TipoPenalidad) {
        dao.save(tipo.toEntity())
    }

    override suspend fun delete(id: Int) {
        val entity = dao.getById(id)
        if (entity != null) dao.delete(entity)
    }

    override suspend fun getById(id: Int): TipoPenalidad? {
        return dao.getById(id)?.toModel()
    }

    override fun getAll(): Flow<List<TipoPenalidad>> {
        return dao.getAll().map { list -> list.map { it.toModel() } }
    }
}

fun TipoPenalidad.toEntity() = TipoPenalidadEntity(
    tipoId = if (tipoId == 0) null else tipoId,
    nombre = nombre,
    descripcion = descripcion,
    puntosDescuento = puntosDescuento
)

fun TipoPenalidadEntity.toModel() = TipoPenalidad(
    tipoId = tipoId ?: 0,
    nombre = nombre,
    descripcion = descripcion,
    puntosDescuento = puntosDescuento
)
