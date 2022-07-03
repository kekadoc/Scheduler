package app.ui.schedule.create.plan

import app.data.repository.group.GroupsRepository
import app.data.repository.plan.AcademicPlanRepository
import app.schedule.plan.GroupPlan
import common.extensions.container
import common.logger.Logger
import common.view_model.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

class AcademicPlanViewModel(
    private val groupsRepository: GroupsRepository,
    private val academicPlanRepository: AcademicPlanRepository
) : ViewModel(), ContainerHost<AcademicPlanState, Unit> {

    override val container = container<AcademicPlanState, Unit>(AcademicPlanState(plan = academicPlanRepository.plan.getAll().values.toList()))

    init {
        Logger.log("AcademicPlanViewModel $academicPlanRepository ${academicPlanRepository.plan} ${academicPlanRepository.plan.getAll()}")
        groupsRepository.allGroups.mapNotNull { it.getOrNull() }.onEach { groups ->
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