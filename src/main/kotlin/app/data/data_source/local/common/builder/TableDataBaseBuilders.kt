package app.data.data_source.local.common.builder

import app.data.data_source.local.common.BaseTableDatabase
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.sql.Table

fun <T : Table, E : Entity<Id>, EC : EntityClass<Id, E>, Id : Comparable<Id>> tableDatabase(
    table: T,
    entityClass: EC
): BaseTableDatabase<T, E, EC, Id> {
    return BaseTableDatabase(table, entityClass)
}

fun <T : Table, E : Entity<Long>, EC : EntityClass<Long, E>> tableLongIdDatabase(
    table: T,
    entityClass: EC
): BaseTableDatabase<T, E, EC, Long> {
    return tableDatabase(table, entityClass)
}