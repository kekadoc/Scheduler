package app.ui.schedule.create.plan

import common.extensions.container
import common.view_model.ViewModel
import data.repository.group.GroupRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import schedule.plan.AcademicPlan
import schedule.plan.GroupPlan

class AcademicPlanViewModel(
    private val groupRepository: GroupRepository,
    private val academicPlan: AcademicPlan
) : ViewModel(), ContainerHost<AcademicPlanState, Unit> {

    override val container = container<AcademicPlanState, Unit>(AcademicPlanState(plan = academicPlan.getAll().values.toList()))

    init {
        groupRepository.allGroups.mapNotNull { it.getOrNull() }.onEach { groups ->
            intent { reduce { state.copy(availableGroups = groups) } }
        }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    fun setPlan(plan: GroupPlan, index: Int) = intent {
        reduce { state.copy(plan = state.plan.toMutableList().apply { set(index, plan) }) }
    }
    fun addPlan(plan: GroupPlan) = intent {
        reduce { state.copy(plan = state.plan.toMutableList().apply { add(plan) }) }
    }

    fun deletePlan(plan: GroupPlan) = intent {
        reduce { state.copy(plan = state.plan.toMutableList().apply { remove(plan) }) }
    }

}