package schedule.rule.discipline

import common.extensions.requireNotNull
import domain.model.*
import schedule.plan.AcademicPlan

class TeachingRule {

    private val options = mutableMapOf<Teaching, Option>()


    fun add(teaching: Teaching, option: Option) {
        options[teaching] = option
    }

    fun get(teaching: Teaching): Option {
        return options[teaching].requireNotNull()
    }

    data class Option(
        var order: Int = 0,
        var planFillingType: PlanFillingType = PlanFillingType.Evenly,
        var room: Room,
        var teacher: Teacher
    )

}

fun AcademicPlan.addPlan(group: Group, weekCount: Int, block: AcademicPlan.GroupPlan.() -> Unit) {
    val plan = AcademicPlan.GroupPlan(weekCount)
    block(plan)
    set(group, plan)
}