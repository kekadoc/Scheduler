package data.data_source.local.unit.teacher

import common.data.AbstractDataSource
import data.data_source.local.common.builder.tableLongIdDatabase
import data.data_source.local.unit.teacher.dao.TeacherEntity
import data.data_source.local.unit.teacher.dao.TeacherTable

class TeacherLocalDataSourceImpl : AbstractDataSource<Long, TeacherEntity>(), TeacherLocalDataSource {

    private val tableDatabase = tableLongIdDatabase(TeacherTable, TeacherEntity)


    override suspend fun getAll(): Result<List<TeacherEntity>> {
        return tableDatabase.all()
    }

    override suspend fun add(lastName: String, firstName: String, middleName: String, speciality: String): Result<TeacherEntity> {
        return tableDatabase.create {
            this.lastName = lastName
            this.firstName = firstName
            this.middleName = middleName
            this.speciality = speciality
        }.onSuccess { onCreate(it.id.value, it) }
    }

    override suspend fun get(id: Long): Result<TeacherEntity> {
        return tableDatabase.read(id)
    }

    override suspend fun update(id: Long, updater: TeacherEntity.() -> Unit): Result<TeacherEntity> {
        return tableDatabase.update(id, updater).onSuccess { onCreate(it.id.value, it) }
    }

    override suspend fun delete(key: Long): Result<TeacherEntity> {
        return tableDatabase.delete(key).onSuccess { onCreate(it.id.value, it) }
    }

    override suspend fun clear(): Result<Unit> {
        return tableDatabase.clear()
    }

}