package app.data.data_source.local.unit.teacher

import app.data.data_source.local.unit.teacher.dao.TeacherEntity
import common.data.DataSource

interface TeacherLocalDataSource : DataSource<Long, TeacherEntity> {

    suspend fun add(lastName: String, firstName: String, middleName: String, speciality: String): Result<TeacherEntity>

    suspend fun get(id: Long): Result<TeacherEntity>

    suspend fun update(id: Long, updater: TeacherEntity.() -> Unit): Result<TeacherEntity>

    suspend fun delete(id: Long): Result<TeacherEntity>

    suspend fun clear(): Result<Unit>
}