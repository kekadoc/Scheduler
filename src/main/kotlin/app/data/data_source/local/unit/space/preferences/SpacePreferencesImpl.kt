package app.data.data_source.local.unit.space.preferences

import app.data.data_source.local.unit.space.dao.SpaceEntity
import common.extensions.emptyString
import java.util.prefs.Preferences
import kotlin.random.Random

class SpacePreferencesImpl : SpacePreferences {

    private val preferences = Preferences.userRoot().node(NODE_NAME)
    private val spacesById = preferences.node(NODE_SPACES_BY_ID)

    override suspend fun getAllSpaces(): Set<SpaceEntity> {
        val idsString = preferences.get(KEY_SPACE_IDS, emptyString())
        if (idsString.isNullOrEmpty()) return emptySet()
        val spaces = idsString
            .split(IDS_SEPARATOR)
            .mapNotNull { it.toLongOrNull() }
            .mapNotNull { id ->
                val spaceName = spacesById.get(id.toString(), emptyString())
                if (spaceName.isNullOrEmpty()) null
                else SpaceEntity(id = id, spaceName)
            }
            .toSet()
        return spaces
    }

    override suspend fun clear() {
        preferences.clear()
        spacesById.clear()
    }

    override suspend fun setAllSpaces(spaces: Set<SpaceEntity>) {
        val idsString = spaces.joinToString(separator = IDS_SEPARATOR) { it.id.toString() }
        preferences.put(KEY_SPACE_IDS, idsString)
        spacesById.clear()
        spaces.forEach { space ->
            spacesById.put(space.id.toString(), space.name)
        }
    }

    override suspend fun getSpace(id: Long): SpaceEntity? {
        val spaceName = spacesById.get(id.toString(), emptyString())
        if (spaceName.isNullOrEmpty()) return null
        return SpaceEntity(id = id, name = spaceName)
    }

    override suspend fun isExist(id: Long): Boolean {
        val idsString = preferences.get(KEY_SPACE_IDS, emptyString())
        val ids =  idsString
            .split(IDS_SEPARATOR)
            .mapNotNull { it.toLongOrNull() }
        return ids.contains(id)
    }

    override suspend fun addSpace(name: String): SpaceEntity {
        val all = getAllSpaces()
        all.find { it.name == name }?.also { return it }
        val ids = all.map { it.id }
        var id: Long = Random.nextLong(1, Long.MAX_VALUE)
        while (ids.contains(id)) { id = Random.nextLong(1, Long.MAX_VALUE) }
        val spaceEntity = SpaceEntity(id = id, name = name)
        val newSpaceSet = all.toMutableSet().apply {
            add(spaceEntity)
        }
        try {
            setAllSpaces(newSpaceSet)
        } catch (e: Throwable) {
            setAllSpaces(all)
            throw e
        }
        return spaceEntity
    }

    override suspend fun deleteSpace(id: Long): SpaceEntity? {
        val all = getAllSpaces()
        val spaceEntity = all.find { it.id == id } ?: return null
        val newSpaceSet = all.toMutableSet().apply { remove(spaceEntity) }
        try {
            setAllSpaces(newSpaceSet)
        } catch (e: Throwable) {
            setAllSpaces(all)
            throw e
        }
        return spaceEntity
    }

    override suspend fun deleteActive() {
        preferences.remove(KEY_ACTIVE_SPACE)
    }

    override suspend fun setActive(spaceEntity: SpaceEntity) {
        preferences.putLong(KEY_ACTIVE_SPACE, spaceEntity.id)
    }

    override suspend fun getActive(): SpaceEntity? {
        val activeId = preferences.getLong(KEY_ACTIVE_SPACE, 0)
        if (activeId == 0L) return null
        return getSpace(activeId)
    }


    private companion object {
        private const val NODE_NAME = "spaces"
        private const val NODE_SPACES_BY_ID = "by_id"
        private const val KEY_ACTIVE_SPACE = "active"
        private const val KEY_SPACE_IDS = "space_ids"
        private const val IDS_SEPARATOR = "/"
    }

}