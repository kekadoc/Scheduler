package domain.model

import java.time.DayOfWeek

data class Schedule(
    val days: Map<DayOfWeek, Map<StudentGroup, Map<Int, AcademicSubject>>>
)