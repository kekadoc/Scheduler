package data.repository.space

import domain.model.Space
import kotlinx.coroutines.flow.Flow

interface SpaceRepository {

    fun saveActiveSpace(space: Space): Flow<Result<Unit>>

    fun getActiveSpace(): Flow<Result<Space>>
}