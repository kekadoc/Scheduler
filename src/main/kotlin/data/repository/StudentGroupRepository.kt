package data.repository

import domain.model.StudentGroup
import kotlinx.coroutines.flow.Flow

interface StudentGroupRepository {

    val allStudentGroups: Flow<List<StudentGroup>>


    fun getStudentGroup(id: Long): Flow<Result<StudentGroup>>

    fun addStudentGroup(name: String): Flow<Result<StudentGroup>>

    fun deleteStudentGroup(id: Long): Flow<Result<StudentGroup>>

    fun updateStudentGroup(studentGroup: StudentGroup): Flow<Result<StudentGroup>>
}