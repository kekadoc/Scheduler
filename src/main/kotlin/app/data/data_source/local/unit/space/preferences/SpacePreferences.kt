package app.data.data_source.local.unit.space.preferences

import app.data.data_source.local.unit.space.dao.SpaceEntity

interface SpacePreferences {

    suspend fun getAllSpaces(): Set<SpaceEntity>

    suspend fun setAllSpaces(spaces: Set<SpaceEntity>)

    suspend fun getSpace(id: Long): SpaceEntity?

    suspend fun isExist(id: Long): Boolean

    suspend fun addSpace(name: String): SpaceEntity

    suspend fun deleteSpace(id: Long): SpaceEntity?

    suspend fun clear()

    suspend fun deleteActive()

    suspend fun setActive(spaceEntity: SpaceEntity)

    suspend fun getActive(): SpaceEntity?
}