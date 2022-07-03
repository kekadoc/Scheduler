package app.data.data_source.local.unit.group

import common.data.AbstractDataSource
import app.data.data_source.local.common.builder.tableLongIdDatabase
import app.data.data_source.local.unit.group.dao.GroupEntity
import app.data.data_source.local.unit.group.dao.GroupTable

class GroupLocalDataSourceImpl : AbstractDataSource<Long, GroupEntity>(), GroupLocalDataSource {

    private val tableDatabase = tableLongIdDatabase(GroupTable, GroupEntity)


    override suspend fun getAll(): Result<List<GroupEntity>> {
        return tableDatabase.all()
    }

    override suspend fun add(name: String): Result<GroupEntity> {
        return tableDatabase.create {
            this.name = name
        }.onSuccess { onCreate(it.id.value, it) }
    }

    override suspend fun get(id: Long): Result<GroupEntity> {
        return tableDatabase.read(id)
    }

    override suspend fun update(id: Long, updater: GroupEntity.() -> Unit): Result<GroupEntity> {
        return tableDatabase.update(id, updater).onSuccess { onCreate(it.id.value, it) }
    }

    override suspend fun delete(id: Long): Result<GroupEntity> {
        return tableDatabase.delete(id).onSuccess { onCreate(it.id.value, it) }
    }

    override suspend fun clear(): Result<Unit> {
        return tableDatabase.clear()
    }

}