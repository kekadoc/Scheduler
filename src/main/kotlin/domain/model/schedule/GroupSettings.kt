package domain.model.schedule

import domain.model.AcademicHour
import domain.model.AcademicSubject
import domain.model.StudentGroup

data class GroupSettings(
    val group: StudentGroup,
    val lessons: Map<AcademicSubject, AcademicHour> = emptyMap()
)