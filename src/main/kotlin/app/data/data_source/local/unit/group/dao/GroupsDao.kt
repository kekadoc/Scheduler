package app.data.data_source.local.unit.group.dao

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object GroupTable : LongIdTable() {
    val name = varchar("name", 50)
}

class GroupEntity(id: EntityID<Long>) : LongEntity(id) {

    var name by GroupTable.name

    companion object : LongEntityClass<GroupEntity>(GroupTable)
}