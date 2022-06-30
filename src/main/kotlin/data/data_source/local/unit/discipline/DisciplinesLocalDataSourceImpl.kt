package data.data_source.local.unit.discipline

import common.data.AbstractDataSource
import data.data_source.local.common.builder.tableLongIdDatabase
import data.data_source.local.unit.discipline.dao.DisciplineEntity
import data.data_source.local.unit.discipline.dao.DisciplinesTable

class DisciplinesLocalDataSourceImpl : AbstractDataSource<Long, DisciplineEntity>(), DisciplineLocalDataSource {

    private val tableDatabase = tableLongIdDatabase(DisciplinesTable, DisciplineEntity)


    override suspend fun getAll(): Result<List<DisciplineEntity>> {
        return tableDatabase.all()
    }

    override suspend fun add(name: String, teachers: List<Long>, rooms: List<Long>): Result<DisciplineEntity> {
        return tableDatabase.create {
            this.name = name
            this.teachers = teachers
            this.rooms = rooms
        }.onSuccess { onCreate(it.id.value, it) }
    }

    override suspend fun get(id: Long): Result<DisciplineEntity> {
        return tableDatabase.read(id)
    }

    override suspend fun update(id: Long, updater: DisciplineEntity.() -> Unit): Result<DisciplineEntity> {
        return tableDatabase.update(id, updater).onSuccess { onCreate(it.id.value, it) }
    }

    override suspend fun delete(key: Long): Result<DisciplineEntity> {
        return tableDatabase.delete(key).onSuccess { onCreate(it.id.value, it) }
    }

    override suspend fun clear(): Result<Unit> {
        return tableDatabase.clear()
    }
}