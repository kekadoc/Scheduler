package app.data.data_source.local.unit.room.dao

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object RoomsTable : LongIdTable() {
    val name = varchar("name", 50)
}

class RoomEntity(id: EntityID<Long>) : LongEntity(id) {

    var name by RoomsTable.name

    companion object : LongEntityClass<RoomEntity>(RoomsTable)
}