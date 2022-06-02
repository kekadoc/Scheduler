package data.data_source.local.unit.rule.dao

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object RulesTable : LongIdTable() {
    val name = varchar("name", 50)
    val description = varchar("description", 50)
    val enabled = bool("enabled")
}

class RuleEntity(id: EntityID<Long>) : LongEntity(id) {

    var name by RulesTable.name
    var description by RulesTable.description
    var enabled by RulesTable.enabled

    companion object : LongEntityClass<RuleEntity>(RulesTable)
}