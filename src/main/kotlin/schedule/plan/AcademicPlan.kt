package schedule.plan

import common.extensions.requireNotNull
import domain.model.AcademicHour
import domain.model.Discipline
import domain.model.Group
import domain.model.Teaching

class AcademicPlan {

    private val groups = mutableMapOf<Group, GroupPlan>()


    fun getAll(): Map<Group, GroupPlan> {
        return groups
    }

    fun set(group: Group, plan: GroupPlan) {
        groups[group] = plan
    }

    fun getPlan(group: Group): GroupPlan {
        return groups[group].requireNotNull()
    }


    class GroupPlan(
        val weekCount: Int
    ) {

        private val items = mutableMapOf<Teaching, AcademicHour>()


        fun getAll(): Map<Teaching, AcademicHour> {
            return items
        }

        fun get(teaching: Teaching): AcademicHour {
            return items[teaching] ?: 0
        }

        fun set(teaching: Teaching, hours: AcademicHour) {
            if (hours <= 0) {
                items.remove(teaching)
            } else {
                items[teaching] = hours
            }
        }

    }

}

fun AcademicPlan.addPlan(group: Group, weekCount: Int, block: AcademicPlan.GroupPlan.() -> Unit) {
    val plan = AcademicPlan.GroupPlan(weekCount)
    block(plan)
    set(group, plan)
}

fun AcademicPlan.getAllDisciplines(): Set<Discipline> {
    return getAllTeachings().map { it.discipline }.toSet()
}

fun AcademicPlan.getAllTeachings(): Set<Teaching> {
    return getAll().map { it.value.getAll() }.map { it.keys }.flatten().toSet()
}

fun AcademicPlan.getDisciplineToGroups(): Map<Teaching, Set<Group>> {
    val sames: Map<Teaching, MutableSet<Group>> = getAllTeachings().associateWith { mutableSetOf() }
    getAll().forEach { (group, plan) ->
        plan.getAll().forEach { (academicSubject, _) ->
            sames[academicSubject].requireNotNull().add(group)
        }
    }
    return sames
}

fun AcademicPlan.getSameTeachingForGroups(): Map<Teaching, Set<Group>> {
    return getDisciplineToGroups().filter { it.value.size > 1 }
}
