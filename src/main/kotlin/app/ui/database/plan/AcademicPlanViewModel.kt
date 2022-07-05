package app.ui.database.plan

import app.data.repository.discipline.DisciplinesRepository
import app.data.repository.group.GroupsRepository
import app.data.repository.plan.AcademicPlanRepository
import app.data.repository.room.RoomsRepository
import app.data.repository.teacher.TeachersRepository
import app.schedule.plan.AcademicPlan
import common.extensions.container
import common.view_model.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

class AcademicPlanViewModel(
    private val groupsRepository: GroupsRepository,
    private val teachersRepository: TeachersRepository,
    private val roomsRepository: RoomsRepository,
    private val disciplinesRepository: DisciplinesRepository,
    private val academicPlanRepository: AcademicPlanRepository
) : ViewModel(), ContainerHost<AcademicPlanState, Unit> {

    override val container = container<AcademicPlanState, Unit>(
        AcademicPlanState()
    )

    init {
        combine(
            groupsRepository.allGroups.mapNotNull { it.getOrNull() },
            teachersRepository.allTeachers.mapNotNull { it.getOrNull() },
            roomsRepository.allRooms.mapNotNull { it.getOrNull() },
            disciplinesRepository.allDisciplines.mapNotNull { it.getOrNull() },
            academicPlanRepository.allPlans.mapNotNull { it.getOrNull() }
        ) { groups, teachers, rooms, disciplines, plans ->
            intent {
                reduce {
                    state.copy(
                        availableGroups = groups,
                        availableTeachers = teachers,
                        availableRooms = rooms,
                        availableDisciplines = disciplines,
                        plans = plans
                    )
                }
            }
        }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }


    fun createAcademicPlan(plan: AcademicPlan) = intent {
        academicPlanRepository.addPlan(name = plan.name, plans = plan.plans).collect()
    }
    fun updateAcademicPlan(plan: AcademicPlan) = intent {
        academicPlanRepository.updatePlan(plan = plan).collect()
    }
    fun deleteAcademicPlan(plan: AcademicPlan) = intent {
        academicPlanRepository.deletePlan(plan.id).collect()
    }

}