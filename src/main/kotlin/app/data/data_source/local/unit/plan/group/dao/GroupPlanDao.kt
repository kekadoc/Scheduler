package app.data.data_source.local.unit.plan.group.dao

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object GroupPlanTable : LongIdTable() {
    val groupId = long("groupId")
    val weekCount = integer("weekCount")
    val disciplinePlanIds = text("disciplines")
}

class GroupPlanEntity(id: EntityID<Long>) : LongEntity(id) {
    var groupId: Long by GroupPlanTable.groupId
    var weekCount: Int by GroupPlanTable.weekCount
    var disciplinePlanIds: List<Long> by GroupPlanTable.disciplinePlanIds.transform(
        toColumn = { list -> list.joinToString("; ") },
        toReal = { column -> column.split("; ").mapNotNull { it.toLongOrNull() } }
    )
    companion object : LongEntityClass<GroupPlanEntity>(GroupPlanTable)
}