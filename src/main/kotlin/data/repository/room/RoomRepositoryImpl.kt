package data.repository.room

import common.data.all
import common.extensions.catchResult
import common.extensions.flowOf
import data.converter.DataConverter
import data.data_source.local.unit.room.RoomsLocalDataSource
import data.data_source.local.unit.room.dao.RoomEntity
import domain.model.Room
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomRepositoryImpl(
    private val localDataSource: RoomsLocalDataSource,
    private val converter: DataConverter
) : RoomRepository {

    override val allRooms: Flow<Result<List<Room>>> = localDataSource.all
        .map(Result.Companion::success)
        .catchResult()
        .map { resultList ->
            resultList.mapCatching { list ->
                list.map { entity -> entity.convert().getOrThrow() }
            }
        }


    override fun getRoom(id: Long): Flow<Result<Room>> {
        return flowOf { localDataSource.get(id).mapCatching { it.convert().getOrThrow() } }
    }

    override fun addRoom(name: String): Flow<Result<Room>> {
        return flowOf {
            localDataSource.add(
                name = name
            ).mapCatching { it.convert().getOrThrow() }
        }
    }

    override fun deleteRoom(id: Long): Flow<Result<Room>> {
        return flowOf { localDataSource.delete(id).mapCatching { it.convert().getOrThrow() } }
    }

    override fun updateRoom(room: Room): Flow<Result<Room>> {
        return flowOf {
            localDataSource.update(room.id) {
                this.name = room.name
            }.mapCatching { it.convert().getOrThrow() }
        }
    }

    override fun clear(): Flow<Result<Unit>> {
        return flowOf { localDataSource.clear() }
    }


    private suspend fun RoomEntity.convert(): Result<Room> {
        return converter.run {
            this@convert.convert()
        }
    }

}