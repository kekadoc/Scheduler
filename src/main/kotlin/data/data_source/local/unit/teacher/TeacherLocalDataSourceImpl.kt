package data.data_source.local.unit.teacher

import data.data_source.local.common.builder.tableLongIdDatabase
import data.data_source.local.unit.teacher.dao.TeacherEntity
import data.data_source.local.unit.teacher.dao.TeacherTable
import domain.model.Teacher
import kotlinx.coroutines.flow.Flow

class TeacherLocalDataSourceImpl : TeacherLocalDataSource {

    private val tableDatabase = tableLongIdDatabase(TeacherTable, TeacherEntity) { entity ->
        Teacher(
            id = entity.id.value,
            firstName = entity.firstName,
            middleName = entity.middleName,
            lastName = entity.lastName,
            speciality = entity.speciality
        )
    }


    override val data: Flow<List<Teacher>>
        get() = tableDatabase.all

    override suspend fun create(creator: TeacherEntity.() -> Unit): Result<Teacher> {
       return tableDatabase.create(creator)
    }

    override suspend fun read(key: Long): Result<Teacher> {
        return tableDatabase.read(key)
    }

    override suspend fun update(key: Long, updater: TeacherEntity.() -> Unit): Result<Teacher> {
        return tableDatabase.update(key, updater)
    }

    override suspend fun delete(key: Long): Result<Teacher> {
        return tableDatabase.delete(key)
    }

}