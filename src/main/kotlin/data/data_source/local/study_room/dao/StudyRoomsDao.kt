package data.data_source.local.study_room.dao

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object StudyRoomsTable : LongIdTable() {
    val name = varchar("name", 50)
    val description = varchar("description", 50)
}

class StudyRoomEntity(id: EntityID<Long>) : LongEntity(id) {

    var name by StudyRoomsTable.name
    var description by StudyRoomsTable.description

    companion object : LongEntityClass<StudyRoomEntity>(StudyRoomsTable)
}