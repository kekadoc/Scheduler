package data.data_source.local.unit.discipline.dao

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object DisciplinesTable : LongIdTable() {
    val name = varchar("name", 50)
    val description = varchar("description", 50)
}

class DisciplineEntity(id: EntityID<Long>) : LongEntity(id) {

    var name by DisciplinesTable.name
    var description by DisciplinesTable.description

    companion object : LongEntityClass<DisciplineEntity>(DisciplinesTable)
}