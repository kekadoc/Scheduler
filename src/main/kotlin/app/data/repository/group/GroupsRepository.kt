package app.data.repository.group

import app.domain.model.Group
import kotlinx.coroutines.flow.Flow

interface GroupsRepository {

    val allGroups: Flow<Result<List<Group>>>


    fun getGroup(id: Long): Flow<Result<Group>>

    fun addGroup(name: String): Flow<Result<Group>>

    fun deleteGroup(id: Long): Flow<Result<Group>>

    fun updateGroup(group: Group): Flow<Result<Group>>

    fun clear(): Flow<Result<Unit>>
}