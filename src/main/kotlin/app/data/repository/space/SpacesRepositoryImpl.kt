package app.data.repository.space

import app.data.data_source.local.unit.space.SpaceLocalDataSource
import app.domain.model.Space
import common.extensions.flowOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SpacesRepositoryImpl(
    private val localDataSource: SpaceLocalDataSource
) : SpacesRepository {

    override fun getAllSpaces(): Flow<Result<Set<Space>>> {
        return flowOf { localDataSource.getAllSpaces() }
    }

    override fun clear(): Flow<Result<Unit>> {
        return flowOf { localDataSource.clear() }
    }

    override fun getSpace(id: Long): Flow<Result<Space>> {
        return flowOf { localDataSource.getSpace(id) }
    }

    override fun isExist(id: Long): Flow<Result<Boolean>> {
        return flow { localDataSource.isExist(id) }
    }

    override fun addSpace(name: String): Flow<Result<Space>> {
        return flowOf { localDataSource.addSpace(name) }
    }

    override fun deleteSpace(id: Long): Flow<Result<Space>> {
        return flowOf { localDataSource.deleteSpace(id) }
    }

    override fun deleteActive(): Flow<Result<Unit>> {
        return flowOf { localDataSource.deleteActive() }
    }

    override fun setActive(space: Space): Flow<Result<Unit>> {
        return flowOf { localDataSource.setActive(space) }
    }

    override fun getActive(): Flow<Result<Space>> {
        return flowOf { localDataSource.getActive() }
    }

}