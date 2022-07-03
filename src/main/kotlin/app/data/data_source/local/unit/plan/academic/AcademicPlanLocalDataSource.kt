package app.data.data_source.local.unit.plan.academic

import app.data.data_source.local.unit.plan.academic.dao.AcademicPlanEntity
import common.data.DataSource

interface AcademicPlanLocalDataSource : DataSource<Long, AcademicPlanEntity> {

    suspend fun add(name: String, groupPlanIds: List<Long>): Result<AcademicPlanEntity>

    suspend fun update(id: Long, updater: AcademicPlanEntity.() -> Unit): Result<AcademicPlanEntity>

    suspend fun get(id: Long): Result<AcademicPlanEntity>

    suspend fun delete(id: Long): Result<AcademicPlanEntity>

    suspend fun clear(): Result<Unit>
}