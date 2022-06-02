package data.data_source.local.unit.space.preferences

import domain.model.Space

interface SpacePreferences {

    suspend fun saveActiveSpace(space: Space)

    suspend fun getActiveSpace(): Space
}