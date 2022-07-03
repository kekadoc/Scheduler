package app.schedule.plan

import app.domain.model.Group
import app.domain.model.Model
import common.extensions.requireNotNull

data class AcademicPlan(
    override val id: Long,
    val name: String,
    val plans: List<GroupPlan>
) : Model {

    fun getPlan(group: Group): GroupPlan {
        return plans.find { it.group == group }.requireNotNull()
    }

    class Builder(
        private val name: String,
        plans: List<GroupPlan.Builder>
    ) {

        private val plans: MutableMap<Group, GroupPlan.Builder> = plans.associateBy { it.group }.toMutableMap()

        fun add(group: Group, builder: GroupPlan.Builder.() -> Unit): Builder = apply {
            plans[group] = GroupPlan.Builder(group, emptyMap()).apply(builder)
        }

    }

}


