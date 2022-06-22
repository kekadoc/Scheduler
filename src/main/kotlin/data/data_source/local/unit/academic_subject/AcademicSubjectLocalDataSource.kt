package data.data_source.local.unit.academic_subject

import common.data.DataFlow
import common.data.crud.CRUD
import data.data_source.local.unit.academic_subject.dao.AcademicSubjectEntity
import domain.model.Discipline
import kotlinx.coroutines.flow.Flow

interface AcademicSubjectLocalDataSource : CRUD<Long, AcademicSubjectEntity, Discipline>, DataFlow<List<Discipline>> {

    override val data: Flow<List<Discipline>>


    override suspend fun create(creator: AcademicSubjectEntity.() -> Unit): Result<Discipline>

    override suspend fun read(key: Long): Result<Discipline>

    override suspend fun update(key: Long, updater: AcademicSubjectEntity.() -> Unit): Result<Discipline>

    override suspend fun delete(key: Long): Result<Discipline>
}