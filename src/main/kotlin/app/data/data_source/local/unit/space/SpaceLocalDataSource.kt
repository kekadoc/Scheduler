package app.data.data_source.local.unit.space

import app.domain.model.Space

interface SpaceLocalDataSource {

    suspend fun getAllSpaces(): Result<Set<Space>>

    suspend fun clear(): Result<Unit>

    suspend fun getSpace(id: Long): Result<Space>

    suspend fun isExist(id: Long): Result<Boolean>

    suspend fun addSpace(name: String): Result<Space>

    suspend fun deleteSpace(id: Long): Result<Space>

    suspend fun deleteActive(): Result<Unit>

    suspend fun setActive(space: Space): Result<Unit>

    suspend fun getActive(): Result<Space>
}