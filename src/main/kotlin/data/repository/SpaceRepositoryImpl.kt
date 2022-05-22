package data.repository

import data.data_source.local.space.SpaceLocalDataSource
import domain.model.Space
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SpaceRepositoryImpl(
    private val localDataSource: SpaceLocalDataSource
) : SpaceRepository {

    override fun saveActiveSpace(space: Space): Flow<Result<Unit>> {
        return flow {
            emit(localDataSource.saveActiveSpace(space))
        }
    }

    override fun getActiveSpace(): Flow<Result<Space>> {
        return flow {
            emit(localDataSource.getActiveSpace())
        }
    }
}