package data.data_source.local.unit.space

import data.data_source.local.unit.space.preferences.SpacePreferences
import domain.model.Space

class SpaceLocalDataSourceImpl(
    private val preferences: SpacePreferences
) : SpaceLocalDataSource {

    override suspend fun saveActiveSpace(space: Space): Result<Unit> {
        return kotlin.runCatching {
            preferences.saveActiveSpace(space)
        }
    }

    override suspend fun getActiveSpace(): Result<Space> {
        return kotlin.runCatching {
            preferences.getActiveSpace()
        }
    }
}