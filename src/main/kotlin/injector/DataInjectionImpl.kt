package injector

import app.SpaceDatabaseLoader
import app.data.repository.space.SpacesRepository
import app.domain.model.Space
import app.domain.model.Space.Companion.isEmpty
import common.extensions.emptyString
import java.util.prefs.Preferences

class DataInjectionImpl(
    private val dataInjectors: Set<DataInjector>,
    private val spacesRepository: SpacesRepository,
    private val provideData: (Space) -> DataRepository
) : DataInjection {

    private val preferences = Preferences.userRoot().node(PREF_NAME)

    private fun setActivated(ids: Set<Long>) {
        preferences.put(KEY_ACTIVATED, ids.joinToString(separator = ACTIVATED_IDS_SEPARATOR) { it.toString() })
    }

    private fun addActivated(id: Long) {
        val current = getActivated()
        val new = current.toMutableSet().apply { add(id) }
        if (current == new) return
        setActivated(new)
    }

    private fun isActivated(id: Long): Boolean {
        val all = getActivated()
        return all.contains(id)
    }

    private fun getActivated(): Set<Long> {
        val str = preferences.get(KEY_ACTIVATED, emptyString())
        if (str.isNullOrEmpty()) return emptySet()
        return str.split(ACTIVATED_IDS_SEPARATOR).mapNotNull { it.toLongOrNull() }.toSet()
    }

    override suspend fun inject() {
        dataInjectors.forEach { injector ->
            val type = injector.type
            val id = injector.id
            if (type == DataInjector.Type.ONLY_ONCE && isActivated(id)) return@forEach
            val space = injector.getSpace(spacesRepository)
            if (space.isEmpty) return@forEach
            SpaceDatabaseLoader.loadSpaceDatabase(space)
            val dataRepo = provideData(space)
            injector.inject(dataRepo)
            addActivated(id)
        }
        SpaceDatabaseLoader.closeSpaceDatabase()
    }

    companion object {
        private const val PREF_NAME = "injections"
        private const val KEY_ACTIVATED = "activated_once"
        private const val ACTIVATED_IDS_SEPARATOR = "/"
    }
}