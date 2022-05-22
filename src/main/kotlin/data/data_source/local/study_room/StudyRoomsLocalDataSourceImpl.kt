package data.data_source.local.study_room

import data.data_source.local.common.builder.tableLongIdDatabase
import data.data_source.local.study_room.dao.StudyRoomEntity
import data.data_source.local.study_room.dao.StudyRoomsTable
import domain.model.StudyRoom
import kotlinx.coroutines.flow.Flow

class StudyRoomsLocalDataSourceImpl : StudyRoomsLocalDataSource {

    private val tableDatabase = tableLongIdDatabase(StudyRoomsTable, StudyRoomEntity) { entity ->
        StudyRoom(
            id = entity.id.value,
            name = entity.name,
            description = entity.description,
        )
    }


    override val data: Flow<List<StudyRoom>>
        get() = tableDatabase.all

    override suspend fun create(creator: StudyRoomEntity.() -> Unit): Result<StudyRoom> {
       return tableDatabase.create(creator)
    }

    override suspend fun read(key: Long): Result<StudyRoom> {
        return tableDatabase.read(key)
    }

    override suspend fun update(key: Long, updater: StudyRoomEntity.() -> Unit): Result<StudyRoom> {
        return tableDatabase.update(key, updater)
    }

    override suspend fun delete(key: Long): Result<StudyRoom> {
        return tableDatabase.delete(key)
    }

}