package app.data.repository.teacher

import app.domain.model.Teacher
import kotlinx.coroutines.flow.Flow

interface TeachersRepository {

    val allTeachers: Flow<Result<List<Teacher>>>


    fun getTeacher(id: Long): Flow<Result<Teacher>>

    fun addTeacher(lastName: String, firstName: String, middleName: String, speciality: String): Flow<Result<Teacher>>

    fun deleteTeacher(id: Long): Flow<Result<Teacher>>

    fun updateTeacher(teacher: Teacher): Flow<Result<Teacher>>

    fun clear(): Flow<Result<Unit>>
}