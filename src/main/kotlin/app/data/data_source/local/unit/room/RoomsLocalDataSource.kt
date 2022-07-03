package app.data.data_source.local.unit.room

import app.data.data_source.local.unit.room.dao.RoomEntity
import common.data.DataSource

interface RoomsLocalDataSource : DataSource<Long, RoomEntity> {

    suspend fun add(name: String): Result<RoomEntity>

    suspend fun get(id: Long): Result<RoomEntity>

    suspend fun update(id: Long, updater: RoomEntity.() -> Unit): Result<RoomEntity>

    suspend fun delete(id: Long): Result<RoomEntity>

    suspend fun clear(): Result<Unit>
}