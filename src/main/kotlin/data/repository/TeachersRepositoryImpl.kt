package data.repository

import common.extensions.flowOf
import data.data_source.local.unit.teacher.TeacherLocalDataSource
import domain.model.Teacher
import kotlinx.coroutines.flow.Flow

class TeachersRepositoryImpl(
    private val localDataSource: TeacherLocalDataSource
) : TeachersRepository {

    override val allTeachers: Flow<List<Teacher>>
        get() = localDataSource.data


    override fun getTeacher(id: Long): Flow<Result<Teacher>> {
        return flowOf {
            localDataSource.read(id)
        }
    }

    override fun addTeacher(firstName: String, middleName: String, lastName: String): Flow<Result<Teacher>> {
        return flowOf {
            localDataSource.create {
                this.firstName = firstName
                this.middleName = middleName
                this.lastName = lastName
            }
        }
    }

    override fun deleteTeacher(id: Long): Flow<Result<Teacher>> {
        return flowOf {
            localDataSource.delete(id)
        }
    }

    override fun updateTeacher(teacher: Teacher): Flow<Result<Teacher>> {
        return flowOf {
            localDataSource.update(teacher.id) {
                this.firstName = teacher.firstName
                this.middleName = teacher.middleName
                this.lastName = teacher.lastName
            }
        }
    }

}