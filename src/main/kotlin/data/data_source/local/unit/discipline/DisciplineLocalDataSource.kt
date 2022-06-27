package data.data_source.local.unit.discipline

import common.data.DataSource
import data.data_source.local.unit.discipline.dao.DisciplineEntity

interface DisciplineLocalDataSource : DataSource<Long, DisciplineEntity> {

    suspend fun add(name: String, teachers: List<Long>, rooms: List<Long>): Result<DisciplineEntity>

    suspend fun get(id: Long): Result<DisciplineEntity>

    suspend fun update(id: Long, updater: DisciplineEntity.() -> Unit): Result<DisciplineEntity>

    suspend fun delete(key: Long): Result<DisciplineEntity>

}