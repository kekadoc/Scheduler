package domain.model.plan

import domain.model.AcademicHour
import domain.model.Group
import domain.model.Teaching

data class GroupPlan(
    val group: Group,
    val plan: List<Pair<Teaching, AcademicHour>> = emptyList()
)