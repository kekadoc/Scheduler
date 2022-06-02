package data.data_source.local.unit.space.preferences

import common.extensions.emptyString
import domain.model.Space
import java.lang.IllegalStateException
import java.util.prefs.Preferences

class SpacePreferencesImpl : SpacePreferences {

    private val preferences = Preferences.userRoot().node(NODE_NAME)

    override suspend fun saveActiveSpace(space: Space) {
        preferences.put(KEY_ACTIVE_SPACE, space.name)
    }

    override suspend fun getActiveSpace(): Space {
        return preferences.get(KEY_ACTIVE_SPACE, emptyString()).let {
            if (it.isNullOrEmpty()) {
                throw IllegalStateException()
            }
            Space(id = 0, name = it)
        }
    }


    companion object {
        private const val NODE_NAME = "spaces"
        private const val KEY_ACTIVE_SPACE = "active_space"
    }

}