package data.repository.group

import common.data.all
import common.extensions.catchResult
import common.extensions.flowOf
import data.converter.DataConverter
import data.data_source.local.unit.group.GroupLocalDataSource
import data.data_source.local.unit.group.dao.GroupEntity
import domain.model.Group
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GroupRepositoryImpl(
    private val localDataSource: GroupLocalDataSource,
    private val converter: DataConverter
) : GroupRepository {

    override val allGroups: Flow<Result<List<Group>>> = localDataSource.all
        .map(Result.Companion::success)
        .catchResult()
        .map { resultList ->
            resultList.mapCatching { list ->
                list.map { entity -> entity.convert().getOrThrow() }
            }
        }


    override fun getGroup(id: Long): Flow<Result<Group>> {
        return flowOf { localDataSource.get(id).mapCatching { it.convert().getOrThrow() } }
    }

    override fun addGroup(name: String): Flow<Result<Group>> {
        return flowOf {
            localDataSource.add(
                name = name
            ).mapCatching { it.convert().getOrThrow() }
        }
    }

    override fun deleteGroup(id: Long): Flow<Result<Group>> {
        return flowOf { localDataSource.delete(id).mapCatching { it.convert().getOrThrow() } }
    }

    override fun updateGroup(group: Group): Flow<Result<Group>> {
        return flowOf {
            localDataSource.update(group.id) {
                this.name = group.name
            }.mapCatching { it.convert().getOrThrow() }
        }
    }

    override fun clear(): Flow<Result<Unit>> {
        return flowOf { localDataSource.clear() }
    }


    private suspend fun GroupEntity.convert(): Result<Group> {
        return converter.run {
            this@convert.convert()
        }
    }

}