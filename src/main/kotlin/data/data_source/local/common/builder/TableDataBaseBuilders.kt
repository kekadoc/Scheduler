package data.data_source.local.common.builder

import data.data_source.local.common.AbstractTableDatabase
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.sql.Table

fun <T : Table, E : Entity<Id>, EC : EntityClass<Id, E>, Id : Comparable<Id>, Model> tableDatabase(
    table: T,
    entityClass: EC,
    converter: suspend (E) -> Model
): AbstractTableDatabase<T, E, EC, Id, Model> {
    return object : AbstractTableDatabase<T, E, EC, Id, Model>(table, entityClass) {
        override suspend fun convertEntityToModel(entity: E): Model {
            return converter(entity)
        }
    }
}

fun <T : Table, E : Entity<Long>, EC : EntityClass<Long, E>, Model> tableLongIdDatabase(
    table: T,
    entityClass: EC,
    converter: suspend (E) -> Model
): AbstractTableDatabase<T, E, EC, Long, Model> {
    return tableDatabase(table, entityClass, converter)
}