package app.schedule.plan

import app.domain.model.*
import common.extensions.orElse

@kotlinx.serialization.Serializable
data class DisciplinePlan(
    val discipline: Discipline,
    val teacher: Teacher = discipline.teachers.firstOrNull().orElse { Teacher.Empty },
    val room: Room = discipline.rooms.firstOrNull().orElse { Room.Empty },
    val works: Map<WorkType, AcademicHour> = emptyMap(),
    val fillingType: PlanFillingType = PlanFillingType.Evenly
) {

    companion object {
        val Empty: DisciplinePlan = DisciplinePlan(
            discipline = Discipline.Empty,
            teacher = Teacher.Empty,
            room = Room.Empty,
            works = emptyMap()
        )
        val DisciplinePlan.isEmpty: Boolean
            get() = this == Empty
    }
}