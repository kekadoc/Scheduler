package data.data_source.local.unit.teacher

import common.data.DataSource
import data.data_source.local.unit.teacher.dao.TeacherEntity

interface TeacherLocalDataSource : DataSource<Long, TeacherEntity> {

    suspend fun add(lastName: String, firstName: String, middleName: String, speciality: String): Result<TeacherEntity>

    suspend fun get(id: Long): Result<TeacherEntity>

    suspend fun update(id: Long, updater: TeacherEntity.() -> Unit): Result<TeacherEntity>

    suspend fun delete(key: Long): Result<TeacherEntity>

}