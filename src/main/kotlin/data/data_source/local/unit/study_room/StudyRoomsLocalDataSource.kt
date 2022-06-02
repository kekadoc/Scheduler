package data.data_source.local.unit.study_room

import common.data.DataFlow
import common.data.crud.CRUD
import data.data_source.local.unit.study_room.dao.StudyRoomEntity
import domain.model.StudyRoom
import kotlinx.coroutines.flow.Flow

interface StudyRoomsLocalDataSource : CRUD<Long, StudyRoomEntity, StudyRoom>, DataFlow<List<StudyRoom>> {

    override val data: Flow<List<StudyRoom>>


    override suspend fun create(creator: StudyRoomEntity.() -> Unit): Result<StudyRoom>

    override suspend fun read(key: Long): Result<StudyRoom>

    override suspend fun update(key: Long, updater: StudyRoomEntity.() -> Unit): Result<StudyRoom>

    override suspend fun delete(key: Long): Result<StudyRoom>
}