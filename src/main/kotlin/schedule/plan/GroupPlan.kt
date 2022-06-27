package schedule.plan

import common.extensions.requireNotNull
import domain.model.*

data class GroupPlan(
    val group: Group,
    /**
     * Кол-во учебных недель
     */
    val weekCount: Int = 18, // TODO: 27.06.2022 Hardcode
    val items: MutableMap<Discipline, DisciplinePlan> = mutableMapOf()
) {

    constructor(group: Group, weekCount: Int, items: List<DisciplinePlan>) : this(
        group = group,
        weekCount = weekCount,
        items = items.associateBy { it.discipline }.toMutableMap()
    )

    fun getAll(): Map<Discipline, DisciplinePlan> {
        return items
    }

    fun getHours(discipline: Discipline, workType: WorkType): AcademicHour {
        return items[discipline].requireNotNull().works[workType].requireNotNull()
    }

    fun set(
        discipline: Discipline,
        workType: WorkType,
        hours: AcademicHour,
        fillingType: PlanFillingType = PlanFillingType.Evenly
    ) {
        val plan = items.getOrPut(discipline) { DisciplinePlan(discipline) }
        val new = plan.copy(
            discipline = discipline,
            works = plan.works.toMutableMap().apply { put(workType, hours) },
            fillingType = fillingType
        )
        items[discipline] = new
    }

}