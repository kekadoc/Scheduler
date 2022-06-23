package data.data_source.local.unit.discipline

import common.data.DataFlow
import common.data.crud.CRUD
import data.data_source.local.unit.discipline.dao.DisciplineEntity
import domain.model.Discipline
import kotlinx.coroutines.flow.Flow

interface DisciplineLocalDataSource : CRUD<Long, DisciplineEntity, Discipline>, DataFlow<List<Discipline>> {

    override val data: Flow<List<Discipline>>


    override suspend fun create(creator: DisciplineEntity.() -> Unit): Result<Discipline>

    override suspend fun read(key: Long): Result<Discipline>

    override suspend fun update(key: Long, updater: DisciplineEntity.() -> Unit): Result<Discipline>

    override suspend fun delete(key: Long): Result<Discipline>
}