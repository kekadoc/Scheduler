package data.repository

import common.extensions.flowOf
import data.data_source.local.unit.student_group.StudentGroupLocalDataSource
import domain.model.Group
import kotlinx.coroutines.flow.Flow

class StudentGroupRepositoryImpl(
    private val localDataSource: StudentGroupLocalDataSource
) : StudentGroupRepository {

    override val allGroups: Flow<List<Group>>
        get() = localDataSource.data


    override fun getStudentGroup(id: Long): Flow<Result<Group>> {
        return flowOf {
            localDataSource.read(id)
        }
    }

    override fun addStudentGroup(name: String): Flow<Result<Group>> {
        return flowOf {
            localDataSource.create {
                this.name = name
            }
        }
    }

    override fun deleteStudentGroup(id: Long): Flow<Result<Group>> {
        return flowOf {
            localDataSource.delete(id)
        }
    }

    override fun updateStudentGroup(group: Group): Flow<Result<Group>> {
        return flowOf {
            localDataSource.update(group.id) {
                this.name = group.name
            }
        }
    }

}