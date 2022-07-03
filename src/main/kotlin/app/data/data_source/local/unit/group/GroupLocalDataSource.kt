package app.data.data_source.local.unit.group

import app.data.data_source.local.unit.group.dao.GroupEntity
import common.data.DataSource

interface GroupLocalDataSource : DataSource<Long, GroupEntity> {

    suspend fun add(name: String): Result<GroupEntity>

    suspend fun get(id: Long): Result<GroupEntity>

    suspend fun update(id: Long, updater: GroupEntity.() -> Unit): Result<GroupEntity>

    suspend fun delete(id: Long): Result<GroupEntity>

    suspend fun clear(): Result<Unit>
}