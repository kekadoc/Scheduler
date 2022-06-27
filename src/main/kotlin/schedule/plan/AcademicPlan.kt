package schedule.plan

import common.extensions.requireNotNull
import domain.model.Discipline
import domain.model.Group

class AcademicPlan(
    plan: Map<Group, GroupPlan> = emptyMap()
) {

    private val groups = mutableMapOf<Group, GroupPlan>().apply { putAll(plan) }

    fun set(data: Map<Group, GroupPlan>) {
        data.forEach { (group, plan) ->
            set(group, plan)
        }
    }

    fun getAll(): Map<Group, GroupPlan> {
        return groups
    }

    fun set(group: Group, plan: GroupPlan) {
        groups[group] = plan
    }

    fun getPlan(group: Group): GroupPlan {
        return groups[group].requireNotNull()
    }


    companion object {

        fun AcademicPlan.addPlan(group: Group, weekCount: Int, block: GroupPlan.() -> Unit) {
            val plan = GroupPlan(group, weekCount)
            block(plan)
            set(group, plan)
        }

        fun AcademicPlan.getAllDisciplines(): Set<Discipline> {
            return getAll().map { it.value.getAll() }.map { it.keys }.flatten().toSet()
        }

        fun AcademicPlan.getDisciplineToGroups(): Map<Discipline, Set<Group>> {
            val sames: Map<Discipline, MutableSet<Group>> = getAllDisciplines().associateWith { mutableSetOf() }
            getAll().forEach { (group, plan) ->
                plan.getAll().forEach { (academicSubject, _) ->
                    sames[academicSubject].requireNotNull().add(group)
                }
            }
            return sames
        }
    }

}


