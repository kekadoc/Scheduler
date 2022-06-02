package data.data_source.local.unit.rule

import common.data.DataFlow
import common.data.crud.CRUD
import data.data_source.local.unit.rule.dao.RuleEntity
import domain.model.Rule
import kotlinx.coroutines.flow.Flow

interface RulesLocalDataSource : CRUD<Long, RuleEntity, Rule>, DataFlow<List<Rule>> {

    override val data: Flow<List<Rule>>


    override suspend fun create(creator: RuleEntity.() -> Unit): Result<Rule>

    override suspend fun read(key: Long): Result<Rule>

    override suspend fun update(key: Long, updater: RuleEntity.() -> Unit): Result<Rule>

    override suspend fun delete(key: Long): Result<Rule>
}