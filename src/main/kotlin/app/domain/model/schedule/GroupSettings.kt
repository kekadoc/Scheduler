package app.domain.model.schedule

import app.domain.model.AcademicHour
import app.domain.model.Discipline
import app.domain.model.Group

data class GroupSettings(
    val group: Group,
    val lessons: Map<Discipline, AcademicHour> = emptyMap()
)