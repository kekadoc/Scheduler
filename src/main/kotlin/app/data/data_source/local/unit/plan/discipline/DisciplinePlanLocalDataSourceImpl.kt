package app.data.data_source.local.unit.plan.discipline

import app.data.data_source.local.common.builder.tableLongIdDatabase
import app.data.data_source.local.unit.plan.discipline.dao.DisciplinePlanEntity
import app.data.data_source.local.unit.plan.discipline.dao.DisciplinePlanTable
import app.domain.model.AcademicHour
import app.domain.model.PlanFillingType
import app.domain.model.WorkType
import common.data.AbstractDataSource

class DisciplinePlanLocalDataSourceImpl : AbstractDataSource<Long, DisciplinePlanEntity>(), DisciplinePlanLocalDataSource {

    private val tableDatabase = tableLongIdDatabase(DisciplinePlanTable, DisciplinePlanEntity)


    override suspend fun add(
        disciplineId: Long,
        teacherId: Long,
        roomId: Long,
        works: Map<WorkType, AcademicHour>,
        fillingType: PlanFillingType
    ): Result<DisciplinePlanEntity> {
        return tableDatabase.create {
            this.disciplineId = disciplineId
            this.teacherId = teacherId
            this.roomId = roomId
            this.works = works
            this.fillingType = fillingType
        }.onSuccess { onCreate(it.id.value, it) }
    }

    override suspend fun update(id: Long, updater: DisciplinePlanEntity.() -> Unit): Result<DisciplinePlanEntity> {
        return tableDatabase.update(id, updater).onSuccess { onUpdate(id, it) }
    }

    override suspend fun get(id: Long): Result<DisciplinePlanEntity> {
        return tableDatabase.read(id)
    }

    override suspend fun delete(id: Long): Result<DisciplinePlanEntity> {
        return tableDatabase.delete(id).onSuccess { onDelete(id, it) }
    }

    override suspend fun clear(): Result<Unit> {
        return tableDatabase.clear().onSuccess { onClear() }
    }

    override suspend fun getAll(): Result<List<DisciplinePlanEntity>> {
        return tableDatabase.all()
    }

}