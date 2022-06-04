package app

import app.model.mvi.AppSideEffect
import app.model.mvi.AppState
import common.view_model.ViewModel
import data.repository.SpaceRepository
import kotlinx.coroutines.flow.first
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.internal.RealContainer
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

class ApplicationViewModel(
    private val spaceRepository: SpaceRepository
) : ViewModel(), ContainerHost<AppState, AppSideEffect> {

    override val container: Container<AppState, AppSideEffect> = RealContainer(AppState(), viewModelScope, Container.Settings())

    init {
        println(this)
    }

    fun setSpaceName(spaceName: String?) = intent {
        reduce {
            state.copy(spaceName = spaceName)
        }
    }

    fun init() = intent {
        val activeSpace = spaceRepository.getActiveSpace().first()
            .onSuccess { space ->
                postSideEffect(AppSideEffect.Authorized(space))
            }
            .onFailure {
                postSideEffect(AppSideEffect.Unauthorized)
            }
    }

}