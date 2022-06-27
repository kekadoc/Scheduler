package data.data_source.local.unit.room

import common.data.DataSource
import data.data_source.local.unit.room.dao.RoomEntity

interface RoomsLocalDataSource : DataSource<Long, RoomEntity> {

    suspend fun add(name: String): Result<RoomEntity>

    suspend fun get(id: Long): Result<RoomEntity>

    suspend fun update(id: Long, updater: RoomEntity.() -> Unit): Result<RoomEntity>

    suspend fun delete(key: Long): Result<RoomEntity>

}