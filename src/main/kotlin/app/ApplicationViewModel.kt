package app

import app.data.injector.DataInjection
import app.data.repository.space.SpacesRepository
import app.di.SpaceDatabaseLoader
import app.domain.model.Space
import common.extensions.container
import common.extensions.orElse
import common.view_model.ViewModel
import common.view_model.ViewModelStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

class ApplicationViewModel(
    private val viewModelStore: ViewModelStore,
    private val spacesRepository: SpacesRepository,
    private val injection: DataInjection
) : ViewModel(), ContainerHost<AppState, Unit> {

    override val container: Container<AppState, Unit> = container(
        initialState = AppState(isLoading = true)
    ) {
        start()
    }


    fun authorize(spaceName: String) = intent {
        reduce { state.copy(isAuthorizationProcess = true) }
        delay(1_000)
        val spaces = spacesRepository.getAllSpaces().first().getOrElse { emptyList() }
        val space: Space = spaces.find { it.name == spaceName }.orElse {
            spacesRepository.addSpace(spaceName).first().getOrThrow() // TODO: 02.07.2022 Handle error
        }
        spacesRepository.setActive(space).first().getOrThrow() // TODO: 02.07.2022 Handle error
        SpaceDatabaseLoader.loadSpaceDatabase(space)
        reduce {
            state.copy(
                isAuthorizationProcess = false,
                space = space
            )
        }
    }

    fun logout() = intent {
        spacesRepository.deleteActive().collect()
        releaseViewModels()
        reduce { state.copy(space = Space.Empty) }
    }


    private fun start() = intent {
        reduce { state.copy(isLoading = true) }
        injection.inject()
        spacesRepository.getActive()
            .first()
            .onSuccess { space ->
                SpaceDatabaseLoader.loadSpaceDatabase(space)
                reduce { state.copy(space = space, isLoading = false) }
            }
            .onFailure {
                reduce { state.copy(space = Space.Empty, isLoading = false) }
            }
    }

    private fun releaseViewModels() {
        viewModelStore.viewModelKeys
            .filter { it != ApplicationViewModel::class }
            .forEach { viewModelStore.clear(it) }
    }

}