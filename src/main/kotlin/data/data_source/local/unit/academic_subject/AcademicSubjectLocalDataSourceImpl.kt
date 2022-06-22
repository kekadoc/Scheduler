package data.data_source.local.unit.academic_subject

import data.data_source.local.unit.academic_subject.dao.AcademicSubjectEntity
import data.data_source.local.unit.academic_subject.dao.AcademicSubjectsTable
import data.data_source.local.common.builder.tableLongIdDatabase
import domain.model.Discipline
import kotlinx.coroutines.flow.Flow

class AcademicSubjectLocalDataSourceImpl : AcademicSubjectLocalDataSource {

    private val tableDatabase = tableLongIdDatabase(AcademicSubjectsTable, AcademicSubjectEntity) { entity ->
        Discipline(
            id = entity.id.value,
            name = entity.name,
            description = entity.description,
        )
    }


    override val data: Flow<List<Discipline>>
        get() = tableDatabase.all

    override suspend fun create(creator: AcademicSubjectEntity.() -> Unit): Result<Discipline> {
       return tableDatabase.create(creator)
    }

    override suspend fun read(key: Long): Result<Discipline> {
        return tableDatabase.read(key)
    }

    override suspend fun update(key: Long, updater: AcademicSubjectEntity.() -> Unit): Result<Discipline> {
        return tableDatabase.update(key, updater)
    }

    override suspend fun delete(key: Long): Result<Discipline> {
        return tableDatabase.delete(key)
    }

}