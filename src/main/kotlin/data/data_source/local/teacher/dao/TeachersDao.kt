package data.data_source.local.teacher.dao

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object TeacherTable : LongIdTable() {
    val firstName = varchar("first_name", 50)
    val middleName = varchar("middle_name", 50)
    val lastName = varchar("last_name", 50)
}

class TeacherEntity(id: EntityID<Long>) : LongEntity(id) {

    var firstName by TeacherTable.firstName
    var middleName by TeacherTable.middleName
    var lastName by TeacherTable.lastName

    companion object : LongEntityClass<TeacherEntity>(TeacherTable)
}