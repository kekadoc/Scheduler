package app.data.repository.room

import app.data.converter.DataConverter
import app.data.data_source.local.unit.room.RoomsLocalDataSource
import app.data.data_source.local.unit.room.dao.RoomEntity
import app.domain.model.Room
import common.data.all
import common.extensions.catchResult
import common.extensions.flowOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomsRepositoryImpl(
    private val localDataSource: RoomsLocalDataSource,
    private val converter: DataConverter
) : RoomsRepository {

    override val allRooms: Flow<Result<List<Room>>> = localDataSource.all
        .map(Result.Companion::success)
        .catchResult()
        .map { resultList ->
            resultList.mapCatching { list ->
                list.map { entity -> entity.convert() }
            }
        }


    override fun getRoom(id: Long): Flow<Result<Room>> {
        return flowOf { localDataSource.get(id).mapCatching { it.convert() } }
    }

    override fun addRoom(name: String): Flow<Result<Room>> {
        return flowOf {
            localDataSource.add(
                name = name
            ).mapCatching { it.convert() }
        }
    }

    override fun deleteRoom(id: Long): Flow<Result<Room>> {
        return flowOf { localDataSource.delete(id).mapCatching { it.convert() } }
    }

    override fun updateRoom(room: Room): Flow<Result<Room>> {
        return flowOf {
            localDataSource.update(room.id) {
                this.name = room.name
            }.mapCatching { it.convert() }
        }
    }

    override fun clear(): Flow<Result<Unit>> {
        return flowOf { localDataSource.clear() }
    }


    private suspend fun RoomEntity.convert(): Room {
        return converter.run {
            this@convert.convert()
        }
    }

}