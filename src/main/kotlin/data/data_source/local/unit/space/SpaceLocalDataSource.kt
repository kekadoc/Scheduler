package data.data_source.local.unit.space

import domain.model.Space

interface SpaceLocalDataSource {

    suspend fun saveActiveSpace(space: Space): Result<Unit>

    suspend fun getActiveSpace(): Result<Space>
}