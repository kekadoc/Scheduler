package app.data.data_source.local.unit.plan.academic.dao

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object AcademicPlanTable : LongIdTable() {
    val name = varchar("name", Int.MAX_VALUE)
    val groups = text("groups")
}

class AcademicPlanEntity(id: EntityID<Long>) : LongEntity(id) {

    var name by AcademicPlanTable.name
    var groupPlanIds: List<Long> by AcademicPlanTable.groups.transform(
        toColumn = { list -> list.joinToString(separator = "; ") },
        toReal = { column -> column.split("; ").mapNotNull { it.toLongOrNull() } }
    )

    companion object : LongEntityClass<AcademicPlanEntity>(AcademicPlanTable)
}