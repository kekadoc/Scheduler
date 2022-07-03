package app.data.repository.space

import app.domain.model.Space
import kotlinx.coroutines.flow.Flow

interface SpacesRepository {

    fun getAllSpaces(): Flow<Result<Set<Space>>>

    fun clear(): Flow<Result<Unit>>

    fun isExist(id: Long): Flow<Result<Boolean>>

    fun getSpace(id: Long): Flow<Result<Space>>

    fun addSpace(name: String): Flow<Result<Space>>

    fun deleteSpace(id: Long): Flow<Result<Space>>


    fun deleteActive(): Flow<Result<Unit>>

    fun setActive(space: Space): Flow<Result<Unit>>

    fun getActive(): Flow<Result<Space>>
}