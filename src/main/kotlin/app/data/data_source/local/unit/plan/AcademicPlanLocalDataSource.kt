package app.data.data_source.local.unit.plan

import app.data.data_source.local.unit.plan.dao.AcademicPlanEntity
import app.schedule.plan.GroupPlan
import common.data.DataSource

interface AcademicPlanLocalDataSource : DataSource<Long, AcademicPlanEntity> {

    suspend fun add(name: String, groupPlans: List<GroupPlan>): Result<AcademicPlanEntity>

    suspend fun update(id: Long, updater: AcademicPlanEntity.() -> Unit): Result<AcademicPlanEntity>

    suspend fun get(id: Long): Result<AcademicPlanEntity>

    suspend fun delete(id: Long): Result<AcademicPlanEntity>

    suspend fun clear(): Result<Unit>
}