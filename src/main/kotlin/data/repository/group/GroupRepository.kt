package data.repository.group

import domain.model.Group
import kotlinx.coroutines.flow.Flow

interface GroupRepository {

    val allGroups: Flow<Result<List<Group>>>


    fun getGroup(id: Long): Flow<Result<Group>>

    fun addGroup(name: String): Flow<Result<Group>>

    fun deleteGroup(id: Long): Flow<Result<Group>>

    fun updateGroup(group: Group): Flow<Result<Group>>
}