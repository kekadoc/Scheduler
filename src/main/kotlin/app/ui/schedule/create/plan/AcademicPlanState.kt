package app.ui.schedule.create.plan

import app.domain.model.Group
import app.schedule.plan.GroupPlan

data class AcademicPlanState(
    val availableGroups: List<Group> = emptyList(),
    val plan: List<GroupPlan> = emptyList()
)