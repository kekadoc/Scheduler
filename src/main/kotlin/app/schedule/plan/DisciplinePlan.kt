package app.schedule.plan

import app.domain.model.*
import common.extensions.orElse

data class DisciplinePlan(
    override val id: Long,
    val discipline: Discipline,
    val teacher: Teacher = discipline.teachers.firstOrNull().orElse { Teacher.Empty },
    val room: Room = discipline.rooms.firstOrNull().orElse { Room.Empty },
    val works: Map<WorkType, AcademicHour> = emptyMap(),
    val fillingType: PlanFillingType = PlanFillingType.Evenly
) : Model {

    data class Builder(
        val discipline: Discipline,
        val teacher: Teacher = discipline.teachers.firstOrNull().orElse { Teacher.Empty },
        val room: Room = discipline.rooms.firstOrNull().orElse { Room.Empty },
        val works: Map<WorkType, AcademicHour> = emptyMap(),
        val fillingType: PlanFillingType = PlanFillingType.Evenly
    )

    companion object : ModelProvider<DisciplinePlan> {
        override val Empty = DisciplinePlan(id = ModelProvider.EMPTY_ID, discipline = Discipline.Empty)
    }
}