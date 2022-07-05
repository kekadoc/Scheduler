package app.schedule.plan

import app.domain.model.AcademicHour
import app.domain.model.Discipline
import app.domain.model.Group
import app.domain.model.WorkType
import common.extensions.requireNotNull

@kotlinx.serialization.Serializable
data class GroupPlan(
    val group: Group,
    /**
     * Кол-во учебных недель
     */
    val weekCount: Int = 18, // TODO: 27.06.2022 Hardcode
    val items: List<DisciplinePlan>
) {

    fun getDiscipline(discipline: Discipline): DisciplinePlan {
        return items.find { it.discipline == discipline }.requireNotNull()
    }

    fun getHours(discipline: Discipline, workType: WorkType): AcademicHour {
        return getDiscipline(discipline).works[workType].requireNotNull()
    }

    companion object {
        val Empty: GroupPlan = GroupPlan(group = Group.Empty, weekCount = 0, items = emptyList())
        val GroupPlan.isEmpty: Boolean
            get() = this == Empty
    }

/*    class Builder(
        val group: Group,
        items: Map<Discipline, DisciplinePlan.Builder>
    ) {

        var weekCount = 18 // TODO: 03.07.2022
            private set
        val items: List<DisciplinePlan.Builder>
            get() = data.values.toList()

        private val data: MutableMap<Discipline, DisciplinePlan.Builder> = items.toMutableMap()

        fun set(
            discipline: Discipline,
            workType: WorkType,
            hours: AcademicHour,
            fillingType: PlanFillingType = PlanFillingType.Evenly
        ) {
            val plan = data.getOrPut(discipline) { DisciplinePlan.Builder(discipline = discipline) }
            val new = plan.copy(
                discipline = discipline,
                works = plan.works.toMutableMap().apply { put(workType, hours) },
                fillingType = fillingType
            )
            data[discipline] = new
        }

        fun setWeekCount(count: Int) = apply {
            this.weekCount = count
        }

        fun addDiscipline(discipline: DisciplinePlan.Builder) = apply {
            this.data[discipline.discipline] = discipline
        }

    }*/

}