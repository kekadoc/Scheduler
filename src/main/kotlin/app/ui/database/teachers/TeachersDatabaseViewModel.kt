package app.ui.database.teachers

import common.extensions.container
import common.logger.Logger
import common.view_model.ViewModel
import data.repository.teacher.TeachersRepository
import domain.model.Teacher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

class TeachersDatabaseViewModel(
    private val teachersRepository: TeachersRepository
) : ViewModel(), ContainerHost<TeachersDatabaseState, Unit> {

    override val container = container<TeachersDatabaseState, Unit>(TeachersDatabaseState())

    init {
        viewModelScope.launch {
            combine(
                teachersRepository.allTeachers.map { it.getOrNull() }.filterNotNull(),
                flowOf(Unit)
            ) { teachers, _ -> TeachersDatabaseState(teachers) }
                .flowOn(Dispatchers.IO)
                .onEach { newState -> intent { reduce { newState } } }
                .launchIn(viewModelScope)
        }
    }

    fun create(lastName: String, firstName: String, middleName: String, speciality: String) = intent {
        teachersRepository.addTeacher(
            lastName = lastName,
            firstName = firstName,
            middleName = middleName,
            speciality = speciality
        )
            .flowOn(Dispatchers.IO)
            .first()
            .onSuccess { teacher ->
                Logger.log("create successfully $teacher")
            }
            .onFailure { error ->
                Logger.log("create failed $error")
            }

    }

    fun update(teacher: Teacher) = intent {
        teachersRepository.updateTeacher(teacher)
            .flowOn(Dispatchers.IO)
            .first()
            .onSuccess {
                Logger.log("update successfully $teacher")
            }
            .onFailure { error ->
                Logger.log("update failed", error)
            }
    }

    fun delete(teacher: Teacher) = intent {
        teachersRepository.deleteTeacher(teacher.id)
            .flowOn(Dispatchers.IO)
            .first()
            .onSuccess {
                Logger.log("delete successfully $teacher")
            }
            .onFailure { error ->
                Logger.log("delete failed", error)
            }
    }

}