package data.data_source.local.unit.student_group

import common.data.DataFlow
import common.data.crud.CRUD
import data.data_source.local.unit.student_group.dao.StudentGroupEntity
import domain.model.StudentGroup
import kotlinx.coroutines.flow.Flow

interface StudentGroupLocalDataSource : CRUD<Long, StudentGroupEntity, StudentGroup>, DataFlow<List<StudentGroup>> {

    override val data: Flow<List<StudentGroup>>


    override suspend fun create(creator: StudentGroupEntity.() -> Unit): Result<StudentGroup>

    override suspend fun read(key: Long): Result<StudentGroup>

    override suspend fun update(key: Long, updater: StudentGroupEntity.() -> Unit): Result<StudentGroup>

    override suspend fun delete(key: Long): Result<StudentGroup>
}