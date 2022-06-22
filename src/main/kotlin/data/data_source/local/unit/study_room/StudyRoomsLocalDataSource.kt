package data.data_source.local.unit.study_room

import common.data.DataFlow
import common.data.crud.CRUD
import data.data_source.local.unit.study_room.dao.StudyRoomEntity
import domain.model.Room
import kotlinx.coroutines.flow.Flow

interface StudyRoomsLocalDataSource : CRUD<Long, StudyRoomEntity, Room>, DataFlow<List<Room>> {

    override val data: Flow<List<Room>>


    override suspend fun create(creator: StudyRoomEntity.() -> Unit): Result<Room>

    override suspend fun read(key: Long): Result<Room>

    override suspend fun update(key: Long, updater: StudyRoomEntity.() -> Unit): Result<Room>

    override suspend fun delete(key: Long): Result<Room>
}