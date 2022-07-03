package app.data.data_source.local.unit.discipline.dao

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object DisciplinesTable : LongIdTable() {
    val name = varchar("name", Int.MAX_VALUE)
    val teachers = varchar("teachers", Int.MAX_VALUE)
    val rooms = varchar("rooms", Int.MAX_VALUE)
}

class DisciplineEntity(id: EntityID<Long>) : LongEntity(id) {

    var name by DisciplinesTable.name
    var teachers by DisciplinesTable.teachers.transform(
        toColumn = { list -> list.joinToString("; ") },
        toReal = { column -> column.split("; ").mapNotNull { it.toLongOrNull() } }
    )
    var rooms by DisciplinesTable.rooms.transform(
        toColumn = { list -> list.joinToString("; ") },
        toReal = { column -> column.split("; ").mapNotNull { it.toLongOrNull() } }
    )

    companion object : LongEntityClass<DisciplineEntity>(DisciplinesTable)
}