package data.data_source.local.unit.group

import common.data.DataSource
import data.data_source.local.unit.group.dao.GroupEntity

interface GroupLocalDataSource : DataSource<Long, GroupEntity> {

    suspend fun add(name: String): Result<GroupEntity>

    suspend fun get(id: Long): Result<GroupEntity>

    suspend fun update(id: Long, updater: GroupEntity.() -> Unit): Result<GroupEntity>

    suspend fun delete(key: Long): Result<GroupEntity>

    suspend fun clear(): Result<Unit>
}