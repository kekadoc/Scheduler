package app.data.data_source.local.unit.plan.group

import app.data.data_source.local.unit.plan.group.dao.GroupPlanEntity
import common.data.DataSource

interface GroupPlanLocalDataSource : DataSource<Long, GroupPlanEntity> {

    suspend fun add(groupId: Long, weekCount: Int, disciplinePlanIds: List<Long>): Result<GroupPlanEntity>

    suspend fun update(id: Long, updater: GroupPlanEntity.() -> Unit): Result<GroupPlanEntity>

    suspend fun get(id: Long): Result<GroupPlanEntity>

    suspend fun delete(id: Long): Result<GroupPlanEntity>

    suspend fun clear(): Result<Unit>
}