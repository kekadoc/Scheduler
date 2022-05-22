package data.data_source.local.student_group.dao

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object StudentGroupTable : LongIdTable() {
    val name = varchar("name", 50)
}

class StudentGroupEntity(id: EntityID<Long>) : LongEntity(id) {

    var name by StudentGroupTable.name

    companion object : LongEntityClass<StudentGroupEntity>(StudentGroupTable)
}