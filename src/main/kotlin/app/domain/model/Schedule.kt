package app.domain.model

import java.time.DayOfWeek

data class Schedule(
    val days: Map<DayOfWeek, Map<Group, Map<Int, Discipline>>>
)