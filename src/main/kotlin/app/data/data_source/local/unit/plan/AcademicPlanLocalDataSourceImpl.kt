package app.data.data_source.local.unit.plan

import app.data.data_source.local.common.builder.tableLongIdDatabase
import app.data.data_source.local.unit.plan.dao.AcademicPlanEntity
import app.data.data_source.local.unit.plan.dao.AcademicPlanTable
import app.schedule.plan.GroupPlan
import common.data.AbstractDataSource

class AcademicPlanLocalDataSourceImpl : AbstractDataSource<Long, AcademicPlanEntity>(), AcademicPlanLocalDataSource {

    private val tableDatabase = tableLongIdDatabase(AcademicPlanTable, AcademicPlanEntity)


    override suspend fun add(name: String, groupPlans: List<GroupPlan>): Result<AcademicPlanEntity> {
        return tableDatabase.create {
            this.name = name
            this.data = groupPlans
        }.onSuccess { onCreate(it.id.value, it) }
    }

    override suspend fun update(id: Long, updater: AcademicPlanEntity.() -> Unit): Result<AcademicPlanEntity> {
        return tableDatabase.update(id, updater).onSuccess { onUpdate(id, it) }
    }

    override suspend fun get(id: Long): Result<AcademicPlanEntity> {
        return tableDatabase.read(id)
    }

    override suspend fun delete(id: Long): Result<AcademicPlanEntity> {
        return tableDatabase.delete(id).onSuccess { onDelete(id, it) }
    }

    override suspend fun clear(): Result<Unit> {
        return tableDatabase.clear().onSuccess { onClear() }
    }

    override suspend fun getAll(): Result<List<AcademicPlanEntity>> {
        return tableDatabase.all()
    }

}