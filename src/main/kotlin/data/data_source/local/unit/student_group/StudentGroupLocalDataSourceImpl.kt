package data.data_source.local.unit.student_group

import data.data_source.local.common.builder.tableLongIdDatabase
import data.data_source.local.unit.student_group.dao.StudentGroupEntity
import data.data_source.local.unit.student_group.dao.StudentGroupTable
import domain.model.Group
import kotlinx.coroutines.flow.Flow

class StudentGroupLocalDataSourceImpl : StudentGroupLocalDataSource {

    private val tableDatabase = tableLongIdDatabase(StudentGroupTable, StudentGroupEntity) { entity ->
        Group(
            id = entity.id.value,
            name = entity.name
        )
    }


    override val data: Flow<List<Group>>
        get() = tableDatabase.all

    override suspend fun create(creator: StudentGroupEntity.() -> Unit): Result<Group> {
       return tableDatabase.create(creator)
    }

    override suspend fun read(key: Long): Result<Group> {
        return tableDatabase.read(key)
    }

    override suspend fun update(key: Long, updater: StudentGroupEntity.() -> Unit): Result<Group> {
        return tableDatabase.update(key, updater)
    }

    override suspend fun delete(key: Long): Result<Group> {
        return tableDatabase.delete(key)
    }

}