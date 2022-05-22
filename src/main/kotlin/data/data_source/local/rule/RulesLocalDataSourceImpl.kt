package data.data_source.local.rule

import data.data_source.local.common.builder.tableLongIdDatabase
import data.data_source.local.rule.dao.RuleEntity
import data.data_source.local.rule.dao.RulesTable
import domain.model.Rule
import kotlinx.coroutines.flow.Flow

class RulesLocalDataSourceImpl : RulesLocalDataSource {

    private val tableDatabase = tableLongIdDatabase(RulesTable, RuleEntity) { entity ->
        Rule(
            id = entity.id.value,
            name = entity.name,
            description = entity.description,
            enabled = entity.enabled
        )
    }


    override val data: Flow<List<Rule>>
        get() = tableDatabase.all

    override suspend fun create(creator: RuleEntity.() -> Unit): Result<Rule> {
       return tableDatabase.create(creator)
    }

    override suspend fun read(key: Long): Result<Rule> {
        return tableDatabase.read(key)
    }

    override suspend fun update(key: Long, updater: RuleEntity.() -> Unit): Result<Rule> {
        return tableDatabase.update(key, updater)
    }

    override suspend fun delete(key: Long): Result<Rule> {
        return tableDatabase.delete(key)
    }

}