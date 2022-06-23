package data.data_source.local.unit.discipline

import data.data_source.local.common.builder.tableLongIdDatabase
import data.data_source.local.unit.discipline.dao.DisciplineEntity
import data.data_source.local.unit.discipline.dao.DisciplinesTable
import domain.model.Discipline
import kotlinx.coroutines.flow.Flow

class DisciplinesLocalDataSourceImpl : DisciplineLocalDataSource {

    private val tableDatabase = tableLongIdDatabase(DisciplinesTable, DisciplineEntity) { entity ->
        Discipline(
            id = entity.id.value,
            name = entity.name,
            description = entity.description,
        )
    }


    override val data: Flow<List<Discipline>>
        get() = tableDatabase.all

    override suspend fun create(creator: DisciplineEntity.() -> Unit): Result<Discipline> {
       return tableDatabase.create(creator)
    }

    override suspend fun read(key: Long): Result<Discipline> {
        return tableDatabase.read(key)
    }

    override suspend fun update(key: Long, updater: DisciplineEntity.() -> Unit): Result<Discipline> {
        return tableDatabase.update(key, updater)
    }

    override suspend fun delete(key: Long): Result<Discipline> {
        return tableDatabase.delete(key)
    }

}