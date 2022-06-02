package data.data_source.local.unit.academic_subject

import common.data.DataFlow
import common.data.crud.CRUD
import data.data_source.local.unit.academic_subject.dao.AcademicSubjectEntity
import domain.model.AcademicSubject
import kotlinx.coroutines.flow.Flow

interface AcademicSubjectLocalDataSource : CRUD<Long, AcademicSubjectEntity, AcademicSubject>, DataFlow<List<AcademicSubject>> {

    override val data: Flow<List<AcademicSubject>>


    override suspend fun create(creator: AcademicSubjectEntity.() -> Unit): Result<AcademicSubject>

    override suspend fun read(key: Long): Result<AcademicSubject>

    override suspend fun update(key: Long, updater: AcademicSubjectEntity.() -> Unit): Result<AcademicSubject>

    override suspend fun delete(key: Long): Result<AcademicSubject>
}