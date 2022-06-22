package data.repository

import domain.model.Group
import kotlinx.coroutines.flow.Flow

interface StudentGroupRepository {

    val allGroups: Flow<List<Group>>


    fun getStudentGroup(id: Long): Flow<Result<Group>>

    fun addStudentGroup(name: String): Flow<Result<Group>>

    fun deleteStudentGroup(id: Long): Flow<Result<Group>>

    fun updateStudentGroup(group: Group): Flow<Result<Group>>
}