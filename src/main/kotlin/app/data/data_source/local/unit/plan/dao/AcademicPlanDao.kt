package app.data.data_source.local.unit.plan.dao

import app.schedule.plan.GroupPlan
import common.serialization.deserialize
import common.serialization.serialize
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object AcademicPlanTable : LongIdTable() {
    val name = text("name")
    val data = text("data")
}

class AcademicPlanEntity(id: EntityID<Long>) : LongEntity(id) {

    var name by AcademicPlanTable.name
    var data: List<GroupPlan> by AcademicPlanTable.data.transform(
        toColumn = { plan -> plan.serialize() },
        toReal = { text -> text.deserialize() }
    )

    companion object : LongEntityClass<AcademicPlanEntity>(AcademicPlanTable)
}