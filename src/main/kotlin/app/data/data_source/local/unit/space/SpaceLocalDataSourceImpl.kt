package app.data.data_source.local.unit.space

import app.data.data_source.local.unit.space.dao.SpaceEntity
import app.data.data_source.local.unit.space.preferences.SpacePreferences
import app.domain.model.Space
import common.extensions.requireNotNull

class SpaceLocalDataSourceImpl(
    private val preferences: SpacePreferences
) : SpaceLocalDataSource {

    override suspend fun getAllSpaces(): Result<Set<Space>> {
        return kotlin.runCatching {
            preferences.getAllSpaces()
                .map { Space(id = it.id, name = it.name) }
                .toSet()
        }
    }

    override suspend fun clear(): Result<Unit> {
        return kotlin.runCatching { preferences.clear() }
    }

    override suspend fun getSpace(id: Long): Result<Space> {
        return kotlin.runCatching {
            preferences.getSpace(id)
                .requireNotNull { "Space by id $id not found" }
                .let { Space(id = it.id, name = it.name) }
        }
    }

    override suspend fun isExist(id: Long): Result<Boolean> {
        return kotlin.runCatching {
            preferences.isExist(id)
        }
    }

    override suspend fun addSpace(name: String): Result<Space> {
        return kotlin.runCatching {
            preferences.addSpace(name).let { Space(id = it.id, name = it.name) }
        }
    }

    override suspend fun deleteSpace(id: Long): Result<Space> {
        return kotlin.runCatching {
            preferences.deleteSpace(id)
                .requireNotNull { "Space by id $id not found" }
                .let { Space(id = it.id, name = it.name) }
        }
    }

    override suspend fun deleteActive(): Result<Unit> {
        return kotlin.runCatching {
            preferences.deleteActive()
        }
    }

    override suspend fun setActive(space: Space): Result<Unit> {
        return kotlin.runCatching {
            preferences.setActive(spaceEntity = SpaceEntity(id = space.id, name = space.name))
        }
    }

    override suspend fun getActive(): Result<Space> {
        return kotlin.runCatching {
            preferences.getActive()
                .requireNotNull { "Active space not found" }
                .let { Space(id = it.id, name = it.name) }
        }
    }

}