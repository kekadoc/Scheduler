package data.data_source.local.unit.academic_subject.dao

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object AcademicSubjectsTable : LongIdTable() {
    val name = varchar("name", 50)
    val description = varchar("description", 50)
}

class AcademicSubjectEntity(id: EntityID<Long>) : LongEntity(id) {

    var name by AcademicSubjectsTable.name
    var description by AcademicSubjectsTable.description

    companion object : LongEntityClass<AcademicSubjectEntity>(AcademicSubjectsTable)
}