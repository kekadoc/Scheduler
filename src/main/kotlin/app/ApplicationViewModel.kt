package app

import app.model.mvi.AppSideEffect
import app.model.mvi.AppState
import common.view_model.ViewModel
import data.repository.discipline.DisciplineRepository
import data.repository.group.GroupRepository
import data.repository.room.RoomRepository
import data.repository.space.SpaceRepository
import data.repository.teacher.TeachersRepository
import injector.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.internal.RealContainer
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

class ApplicationViewModel(
    private val spaceRepository: SpaceRepository,
    private val teachersRepository: TeachersRepository,
    private val groupRepository: GroupRepository,
    private val roomRepository: RoomRepository,
    private val disciplineRepository: DisciplineRepository,
    private val injector: Inject
) : ViewModel(), ContainerHost<AppState, AppSideEffect> {

    override val container: Container<AppState, AppSideEffect> = RealContainer(AppState(), viewModelScope, Container.Settings())

    init {
        viewModelScope.launch {
            teachersRepository.clear().collect()
            groupRepository.clear().collect()
            roomRepository.clear().collect()
            disciplineRepository.clear().collect()
            //injector.injectTeachers()
            //injector.injectRooms()
            //injector.injectDisciplines()
            //injector.injectGroups()
        }
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