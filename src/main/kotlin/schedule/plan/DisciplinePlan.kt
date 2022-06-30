package schedule.plan

import common.extensions.orElse
import domain.model.*

data class DisciplinePlan(
    val discipline: Discipline,
    val teacher: Teacher = discipline.teachers.firstOrNull().orElse { Teacher.Empty },
    val room: Room = discipline.rooms.firstOrNull().orElse { Room.Empty },
    val works: Map<WorkType, AcademicHour> = emptyMap(),
    val fillingType: PlanFillingType = PlanFillingType.Evenly
) {
    companion object {
        val Empty = DisciplinePlan(discipline = Discipline.Empty)
    }
}