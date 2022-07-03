package app.data.data_source.local.unit.plan.discipline.dao

import app.domain.model.AcademicHour
import app.domain.model.PlanFillingType
import app.domain.model.WorkType
import common.serialization.deserialize
import common.serialization.serialize
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object DisciplinePlanTable : LongIdTable() {
    val disciplineId = long("disciplineId")
    val teacherId = long("teacherId")
    val roomId = long("roomId")
    val works = this.text(name = "works")
    val fillingType = text(name = "fillingType")
}

class DisciplinePlanEntity(id: EntityID<Long>) : LongEntity(id) {
    var disciplineId by DisciplinePlanTable.disciplineId
    var teacherId by DisciplinePlanTable.teacherId
    var roomId by DisciplinePlanTable.roomId
    var works: Map<WorkType, AcademicHour> by DisciplinePlanTable.works.transform(
        toColumn = { type -> type.serialize() },
        toReal = { column -> column.deserialize() }
    )
    var fillingType: PlanFillingType by DisciplinePlanTable.fillingType.transform(
        toColumn = { type -> type.serialize() },
        toReal = { column -> column.deserialize() }
    )
    companion object : LongEntityClass<DisciplinePlanEntity>(DisciplinePlanTable)
}