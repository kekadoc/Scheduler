package schedule.plan

import domain.model.*

data class DisciplinePlan(
    val discipline: Discipline,
    val teacher: Teacher = discipline.teachers.first(),
    val room: Room = discipline.rooms.first(),
    val works: Map<WorkType, AcademicHour> = emptyMap(),
    val fillingType: PlanFillingType = PlanFillingType.Evenly
) {
    companion object {
        val Empty = DisciplinePlan(discipline = Discipline.Empty)
    }
}