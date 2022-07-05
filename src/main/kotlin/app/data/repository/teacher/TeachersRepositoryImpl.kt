package app.data.repository.teacher

import app.data.converter.DataConverter
import app.data.data_source.local.unit.teacher.TeacherLocalDataSource
import app.data.data_source.local.unit.teacher.dao.TeacherEntity
import app.domain.model.Teacher
import common.data.all
import common.extensions.catchResult
import common.extensions.flowOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TeachersRepositoryImpl(
    private val localDataSource: TeacherLocalDataSource,
    private val converter: DataConverter
) : TeachersRepository {

    override val allTeachers: Flow<Result<List<Teacher>>> = localDataSource.all
        .map(Result.Companion::success)
        .catchResult()
        .map { resultList ->
            resultList.mapCatching { list ->
                list.map { entity -> entity.convert() }
            }
        }


    override fun getTeacher(id: Long): Flow<Result<Teacher>> {
        return flowOf { localDataSource.get(id).mapCatching { it.convert() } }
    }

    override fun addTeacher(
        lastName: String,
        firstName: String,
        middleName: String,
        speciality: String
    ): Flow<Result<Teacher>> {
        return flowOf {
            localDataSource.add(
                lastName = lastName,
                firstName = firstName,
                middleName = middleName,
                speciality = speciality,
            ).mapCatching { it.convert() }
        }
    }

    override fun deleteTeacher(id: Long): Flow<Result<Teacher>> {
        return flowOf { localDataSource.delete(id).mapCatching { it.convert() } }
    }

    override fun updateTeacher(teacher: Teacher): Flow<Result<Teacher>> {
        return flowOf {
            localDataSource.update(teacher.id) {
                this.lastName = teacher.lastName
                this.firstName = teacher.firstName
                this.middleName = teacher.middleName
                this.speciality = teacher.speciality
            }.mapCatching { it.convert() }
        }
    }

    override fun clear(): Flow<Result<Unit>> {
        return flowOf { localDataSource.clear() }
    }


    private suspend fun TeacherEntity.convert(): Teacher {
        return converter.run {
            this@convert.convert()
        }
    }

}