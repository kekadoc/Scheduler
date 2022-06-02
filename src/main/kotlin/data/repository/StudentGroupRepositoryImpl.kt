package data.repository

import common.extensions.flowOf
import data.data_source.local.unit.student_group.StudentGroupLocalDataSource
import domain.model.StudentGroup
import kotlinx.coroutines.flow.Flow

class StudentGroupRepositoryImpl(
    private val localDataSource: StudentGroupLocalDataSource
) : StudentGroupRepository {

    override val allStudentGroups: Flow<List<StudentGroup>>
        get() = localDataSource.data


    override fun getStudentGroup(id: Long): Flow<Result<StudentGroup>> {
        return flowOf {
            localDataSource.read(id)
        }
    }

    override fun addStudentGroup(name: String): Flow<Result<StudentGroup>> {
        return flowOf {
            localDataSource.create {
                this.name = name
            }
        }
    }

    override fun deleteStudentGroup(id: Long): Flow<Result<StudentGroup>> {
        return flowOf {
            localDataSource.delete(id)
        }
    }

    override fun updateStudentGroup(studentGroup: StudentGroup): Flow<Result<StudentGroup>> {
        return flowOf {
            localDataSource.update(studentGroup.id) {
                this.name = studentGroup.name
            }
        }
    }

}