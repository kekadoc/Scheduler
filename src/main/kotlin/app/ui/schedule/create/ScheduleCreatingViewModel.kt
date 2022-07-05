package app.ui.schedule.create

import app.data.repository.plan.AcademicPlanRepository
import app.domain.model.DayOfWeek
import app.schedule.builder.BuilderUtils
import app.schedule.builder.ScheduleBuilder
import app.schedule.plan.AcademicPlan
import app.schedule.rule.Rules
import app.schedule.rule.room.RoomRule
import app.schedule.rule.student.StudentGroupRule
import app.schedule.rule.teacher.TeacherRule
import common.extensions.container
import common.view_model.ViewModel
import excel.CreateScheduleXLSX
import excel.model.buildExcelModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

data class ScheduleCreatingState(
    val plans: List<AcademicPlan> = emptyList(),
    val selectedPlan: AcademicPlan = AcademicPlan.Empty
)

class ScheduleCreatingViewModel(
    private val planRepository: AcademicPlanRepository
) : ViewModel(), ContainerHost<ScheduleCreatingState, Unit> {

    override val container = container<ScheduleCreatingState, Unit>(ScheduleCreatingState())

    init {
        planRepository.allPlans
            .mapNotNull { it.getOrNull() }
            .onEach { plans -> intent { reduce { state.copy(plans = plans) } } }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    fun selectAcademicPlan(academicPlan: AcademicPlan) = intent {
        reduce { state.copy(selectedPlan = academicPlan) }
    }

    fun buildSchedule() = intent {
        val academicPlan = state.selectedPlan
        val availableGroups = academicPlan.plans.map { it.group }.toSet()
        val maxLessonsInDay = 6 // TODO: 24.06.2022 Mock
        val scheduleBuilder = ScheduleBuilder(maxLessonsInDay, availableGroups)
        val rules = Rules(
            teacherRule = TeacherRule(),
            groupRule = StudentGroupRule(),
            roomRule = RoomRule(),
            availableDays = DayOfWeek.values().take(6).toSet()
        )
        BuilderUtils.build(academicPlan, scheduleBuilder, rules)
        val scheduleExcel = scheduleBuilder.buildExcelModel()
        CreateScheduleXLSX.create(scheduleExcel)
    }

}