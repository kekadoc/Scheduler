package app.data.data_source.local.common

import common.data.CRUD
import common.extensions.requireNotNull
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.dao.Entity as DaoEntity
import org.jetbrains.exposed.dao.EntityClass as DaoEntityClass
import org.jetbrains.exposed.sql.Table as SqlTable

class BaseTableDatabase
<Table : SqlTable, Entity : DaoEntity<Id>, EntityClass : DaoEntityClass<Id, Entity>, Id : Comparable<Id>>
constructor(
    private val table: Table,
    private val entityClass: EntityClass
) : CRUD<Id, Entity> {

    init {
        transaction {
            SchemaUtils.create(table)
        }
    }


    override suspend fun all(): Result<List<Entity>> {
        return kotlin.runCatching {
            newSuspendedTransaction { entityClass.all().toList() }
        }
    }

    override suspend fun create(creator: Entity.() -> Unit): Result<Entity> {
        return kotlin.runCatching {
            newSuspendedTransaction {
                entityClass
                    .new { creator(this) }
            }
        }
    }

    override suspend fun read(key: Id): Result<Entity> {
        return kotlin.runCatching {
            newSuspendedTransaction {
                // TODO: 01.05.2022 Exception NotFound
                entityClass
                    .findById(key)
                    .requireNotNull()
            }
        }
    }


    override suspend fun update(key: Id, updater: Entity.() -> Unit): Result<Entity> {
        return kotlin.runCatching {
            newSuspendedTransaction {
                // TODO: 01.05.2022 Exception NotFound
                entityClass
                    .findById(key)
                    .requireNotNull()
                    .apply(updater)
            }
        }
    }

    override suspend fun delete(key: Id): Result<Entity> {
        return kotlin.runCatching {
            newSuspendedTransaction {
                // TODO: 01.05.2022 Exception NotFound
                entityClass
                    .findById(key)
                    .requireNotNull()
                    .apply { delete() }
            }
        }
    }

    override suspend fun clear(): Result<Unit> {
        return runCatching<Unit> {
            newSuspendedTransaction {
                table.deleteAll()
            }
        }
    }

}