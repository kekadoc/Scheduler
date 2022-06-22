package data.data_source.local.unit.study_room

import data.data_source.local.common.builder.tableLongIdDatabase
import data.data_source.local.unit.study_room.dao.StudyRoomEntity
import data.data_source.local.unit.study_room.dao.StudyRoomsTable
import domain.model.Room
import kotlinx.coroutines.flow.Flow

class StudyRoomsLocalDataSourceImpl : StudyRoomsLocalDataSource {

    private val tableDatabase = tableLongIdDatabase(StudyRoomsTable, StudyRoomEntity) { entity ->
        Room(
            id = entity.id.value,
            name = entity.name,
            description = entity.description,
        )
    }


    override val data: Flow<List<Room>>
        get() = tableDatabase.all

    override suspend fun create(creator: StudyRoomEntity.() -> Unit): Result<Room> {
       return tableDatabase.create(creator)
    }

    override suspend fun read(key: Long): Result<Room> {
        return tableDatabase.read(key)
    }

    override suspend fun update(key: Long, updater: StudyRoomEntity.() -> Unit): Result<Room> {
        return tableDatabase.update(key, updater)
    }

    override suspend fun delete(key: Long): Result<Room> {
        return tableDatabase.delete(key)
    }

}