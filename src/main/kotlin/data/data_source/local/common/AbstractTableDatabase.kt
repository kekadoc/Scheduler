package data.data_source.local.common

import common.data.crud.CRUD
import common.extensions.requireNotNull
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.jetbrains.exposed.dao.Entity as DaoEntity
import org.jetbrains.exposed.dao.EntityClass as DaoEntityClass
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.Table as SqlTable
import org.jetbrains.exposed.sql.transactions.transaction

abstract class AbstractTableDatabase
<Table : SqlTable, Entity : DaoEntity<Id>, EntityClass : DaoEntityClass<Id, Entity>, Id : Comparable<Id>, Model>
constructor(
    private val table: Table,
    private val entityClass: EntityClass
) : CRUD<Id, Entity, Model> {

    private val _all = MutableStateFlow<List<Model>>(emptyList())
    val all: Flow<List<Model>> = _all.asStateFlow()

    init {
        transaction {
            SchemaUtils.create(table)
        }
    }


    protected abstract suspend fun convertEntityToModel(entity: Entity): Model

    protected open suspend fun onModelCreated(model: Model) {
        updateModels()
    }
    protected open suspend fun onModelUpdated(model: Model) {
        updateModels()
    }
    protected open suspend fun onModelDeleted(model: Model) {
        updateModels()
    }

    protected suspend fun updateModels() {
        newSuspendedTransaction {
            val items = entityClass.all().toList().map { convertEntityToModel(it) }
            _all.emit(items)
        }
    }


    override suspend fun create(creator: Entity.() -> Unit): Result<Model> {
        return kotlin.runCatching {
            newSuspendedTransaction {
                entityClass
                    .new { creator(this) }
                    .let { convertEntityToModel(it) }
                    .also { onModelCreated(it) }
            }
        }
    }

    override suspend fun read(key: Id): Result<Model> {
        return kotlin.runCatching {
            newSuspendedTransaction {
                // TODO: 01.05.2022 Exception NotFound
                entityClass
                    .findById(key)
                    .requireNotNull()
                    .let { convertEntityToModel(it) }
            }
        }
    }


    override suspend fun update(key: Id, updater: Entity.() -> Unit): Result<Model> {
        return kotlin.runCatching {
            newSuspendedTransaction {
                // TODO: 01.05.2022 Exception NotFound
                entityClass
                    .findById(key)
                    .requireNotNull()
                    .apply(updater)
                    .let { convertEntityToModel(it) }
                    .also { onModelUpdated(it) }
            }
        }
    }

    override suspend fun delete(key: Id): Result<Model> {
        return kotlin.runCatching {
            newSuspendedTransaction {
                // TODO: 01.05.2022 Exception NotFound
                entityClass
                    .findById(key)
                    .requireNotNull()
                    .apply { delete() }
                    .let { convertEntityToModel(it) }
                    .also { onModelDeleted(it) }
            }
        }
    }

}