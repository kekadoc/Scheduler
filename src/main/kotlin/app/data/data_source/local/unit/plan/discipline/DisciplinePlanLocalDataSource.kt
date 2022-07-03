package app.data.data_source.local.unit.plan.discipline

import app.data.data_source.local.unit.plan.discipline.dao.DisciplinePlanEntity
import app.domain.model.AcademicHour
import app.domain.model.PlanFillingType
import app.domain.model.WorkType
import common.data.DataSource

interface DisciplinePlanLocalDataSource : DataSource<Long, DisciplinePlanEntity> {

    suspend fun add(
        disciplineId: Long,
        teacherId: Long,
        roomId: Long,
        works: Map<WorkType, AcademicHour>,
        fillingType: PlanFillingType
    ): Result<DisciplinePlanEntity>

    suspend fun update(id: Long, updater: DisciplinePlanEntity.() -> Unit): Result<DisciplinePlanEntity>

    suspend fun get(id: Long): Result<DisciplinePlanEntity>

    suspend fun delete(id: Long): Result<DisciplinePlanEntity>

    suspend fun clear(): Result<Unit>
}