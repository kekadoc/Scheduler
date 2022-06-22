package data.data_source.local.unit.student_group

import common.data.DataFlow
import common.data.crud.CRUD
import data.data_source.local.unit.student_group.dao.StudentGroupEntity
import domain.model.Group
import kotlinx.coroutines.flow.Flow

interface StudentGroupLocalDataSource : CRUD<Long, StudentGroupEntity, Group>, DataFlow<List<Group>> {

    override val data: Flow<List<Group>>


    override suspend fun create(creator: StudentGroupEntity.() -> Unit): Result<Group>

    override suspend fun read(key: Long): Result<Group>

    override suspend fun update(key: Long, updater: StudentGroupEntity.() -> Unit): Result<Group>

    override suspend fun delete(key: Long): Result<Group>
}