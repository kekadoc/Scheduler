package app.data.data_source.local.unit.plan.group

import app.data.data_source.local.common.builder.tableLongIdDatabase
import app.data.data_source.local.unit.plan.group.dao.GroupPlanEntity
import app.data.data_source.local.unit.plan.group.dao.GroupPlanTable
import common.data.AbstractDataSource

class GroupPlanLocalDataSourceImpl : AbstractDataSource<Long, GroupPlanEntity>(), GroupPlanLocalDataSource {

    private val tableDatabase = tableLongIdDatabase(GroupPlanTable, GroupPlanEntity)


    override suspend fun add(
        groupId: Long,
        weekCount: Int,
        disciplinePlanIds: List<Long>
    ): Result<GroupPlanEntity> {

        return tableDatabase.create {
            this.groupId = groupId
            this.weekCount = weekCount
            this.disciplineIds = disciplinePlanIds
        }.onSuccess { onCreate(it.id.value, it) }
    }

    override suspend fun update(
        id: Long,
        updater: GroupPlanEntity.() -> Unit
    ): Result<GroupPlanEntity> {
        return tableDatabase.update(id, updater).onSuccess { onUpdate(it.id.value, it) }
    }

    override suspend fun get(id: Long): Result<GroupPlanEntity> {
        return tableDatabase.read(id)
    }

    override suspend fun delete(id: Long): Result<GroupPlanEntity> {
        return tableDatabase.delete(id).onSuccess { onDelete(it.id.value, it) }
    }

    override suspend fun clear(): Result<Unit> {
        return tableDatabase.clear().onSuccess { onClear() }
    }

    override suspend fun getAll(): Result<List<GroupPlanEntity>> {
        return tableDatabase.all()
    }

}