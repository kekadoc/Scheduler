package data.data_source.local.unit.room

import common.data.AbstractDataSource
import data.data_source.local.common.builder.tableLongIdDatabase
import data.data_source.local.unit.room.dao.RoomEntity
import data.data_source.local.unit.room.dao.RoomsTable

class RoomsLocalDataSourceImpl : AbstractDataSource<Long, RoomEntity>(), RoomsLocalDataSource {

    private val tableDatabase = tableLongIdDatabase(RoomsTable, RoomEntity)


    override suspend fun getAll(): Result<List<RoomEntity>> {
        return tableDatabase.all()
    }

    override suspend fun add(name: String): Result<RoomEntity> {
        return tableDatabase.create {
            this.name = name
        }.onSuccess { onCreate(it.id.value, it) }
    }

    override suspend fun get(id: Long): Result<RoomEntity> {
        return tableDatabase.read(id)
    }

    override suspend fun update(id: Long, updater: RoomEntity.() -> Unit): Result<RoomEntity> {
        return tableDatabase.update(id, updater).onSuccess { onCreate(it.id.value, it) }
    }

    override suspend fun delete(key: Long): Result<RoomEntity> {
        return tableDatabase.delete(key).onSuccess { onCreate(it.id.value, it) }
    }

    override suspend fun clear(): Result<Unit> {
        return tableDatabase.clear()
    }

}