package data.data_source.local.unit.student_group

import data.data_source.local.common.builder.tableLongIdDatabase
import data.data_source.local.unit.student_group.dao.StudentGroupEntity
import data.data_source.local.unit.student_group.dao.StudentGroupTable
import domain.model.StudentGroup
import kotlinx.coroutines.flow.Flow

class StudentGroupLocalDataSourceImpl : StudentGroupLocalDataSource {

    private val tableDatabase = tableLongIdDatabase(StudentGroupTable, StudentGroupEntity) { entity ->
        StudentGroup(
            id = entity.id.value,
            name = entity.name
        )
    }


    override val data: Flow<List<StudentGroup>>
        get() = tableDatabase.all

    override suspend fun create(creator: StudentGroupEntity.() -> Unit): Result<StudentGroup> {
       return tableDatabase.create(creator)
    }

    override suspend fun read(key: Long): Result<StudentGroup> {
        return tableDatabase.read(key)
    }

    override suspend fun update(key: Long, updater: StudentGroupEntity.() -> Unit): Result<StudentGroup> {
        return tableDatabase.update(key, updater)
    }

    override suspend fun delete(key: Long): Result<StudentGroup> {
        return tableDatabase.delete(key)
    }

}