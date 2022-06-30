package app.ui.schedule.create.plan

import domain.model.Group
import schedule.plan.GroupPlan

data class AcademicPlanState(
    val availableGroups: List<Group> = emptyList(),
    val plan: List<GroupPlan> = emptyList()
)