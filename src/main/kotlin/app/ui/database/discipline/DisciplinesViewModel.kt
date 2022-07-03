package app.ui.database.discipline

import common.extensions.container
import common.logger.Logger
import common.view_model.ViewModel
import app.data.repository.discipline.DisciplinesRepository
import app.data.repository.room.RoomsRepository
import app.data.repository.teacher.TeachersRepository
import app.domain.model.Discipline
import app.domain.model.Room
import app.domain.model.Teacher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

data class DisciplinesViewState(
    val disciplines: List<Discipline> = emptyList(),
    val availableTeachers: List<Teacher> = emptyList(),
    val availableRooms: List<Room> = emptyList(),
)

class DisciplinesViewModel(
    private val disciplinesRepository: DisciplinesRepository,
    private val teachersRepository: TeachersRepository,
    private val roomsRepository: RoomsRepository
) : ViewModel(), ContainerHost<DisciplinesViewState, Unit> {

    override val container = container<DisciplinesViewState, Unit>(DisciplinesViewState())


    init {
        viewModelScope.launch {
            combine(
                disciplinesRepository.allDisciplines.map { it.getOrNull() }.filterNotNull(),
                teachersRepository.allTeachers.map { it.getOrNull() }.filterNotNull(),
                roomsRepository.allRooms.map { it.getOrNull() }.filterNotNull()
            ) { disciplines, teachers, rooms ->
                DisciplinesViewState(
                    disciplines = disciplines,
                    availableTeachers = teachers,
                    availableRooms = rooms
                )
            }
                .flowOn(Dispatchers.IO)
                .onEach { newState -> intent { reduce { newState } } }
                .launchIn(viewModelScope)
        }
    }


    fun create(name: String, teachers: List<Teacher>, rooms: List<Room>) = intent {
        disciplinesRepository.addDiscipline(name = name, teachers = teachers, rooms = rooms)
            .flowOn(Dispatchers.IO)
            .first()
            .onSuccess { teacher ->
                Logger.log("create successfully $teacher")
            }
            .onFailure { error ->
                Logger.log("create failed $error")
            }

    }

    fun update(discipline: Discipline) = intent {
        disciplinesRepository.updateDiscipline(discipline)
            .flowOn(Dispatchers.IO)
            .first()
            .onSuccess { newRoom ->
                Logger.log("update successfully $newRoom")
            }
            .onFailure { error ->
                Logger.log("update failed", error)
            }
    }

    fun delete(discipline: Discipline) = intent {
        disciplinesRepository.deleteDiscipline(discipline.id)
            .flowOn(Dispatchers.IO)
            .first()
            .onSuccess { deletedRoom ->
                Logger.log("delete successfully $deletedRoom")
            }
            .onFailure { error ->
                Logger.log("delete failed", error)
            }
    }

}