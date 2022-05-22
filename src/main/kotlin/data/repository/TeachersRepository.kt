package data.repository

import domain.model.Teacher
import kotlinx.coroutines.flow.Flow

interface TeachersRepository {

    val allTeachers: Flow<List<Teacher>>


    fun getTeacher(id: Long): Flow<Result<Teacher>>

    fun addTeacher(firstName: String, middleName: String, lastName: String): Flow<Result<Teacher>>

    fun deleteTeacher(id: Long): Flow<Result<Teacher>>

    fun updateTeacher(teacher: Teacher): Flow<Result<Teacher>>
}