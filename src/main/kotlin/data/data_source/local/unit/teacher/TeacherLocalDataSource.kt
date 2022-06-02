package data.data_source.local.unit.teacher

import common.data.DataFlow
import common.data.crud.CRUD
import data.data_source.local.unit.teacher.dao.TeacherEntity
import domain.model.Teacher
import kotlinx.coroutines.flow.Flow

interface TeacherLocalDataSource : CRUD<Long, TeacherEntity, Teacher>, DataFlow<List<Teacher>> {

    override val data: Flow<List<Teacher>>


    override suspend fun create(creator: TeacherEntity.() -> Unit): Result<Teacher>

    override suspend fun read(key: Long): Result<Teacher>

    override suspend fun update(key: Long, updater: TeacherEntity.() -> Unit): Result<Teacher>

    override suspend fun delete(key: Long): Result<Teacher>
}