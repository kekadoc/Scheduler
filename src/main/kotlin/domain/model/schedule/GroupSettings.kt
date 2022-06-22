package domain.model.schedule

import domain.model.AcademicHour
import domain.model.Discipline
import domain.model.Group

data class GroupSettings(
    val group: Group,
    val lessons: Map<Discipline, AcademicHour> = emptyMap()
)