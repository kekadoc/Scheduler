package app.schedule.plan

import app.domain.model.Group
import app.domain.model.Model
import app.domain.model.ModelProvider
import common.extensions.emptyString
import common.extensions.requireNotNull

data class AcademicPlan(
    override val id: Long,
    val name: String,
    val plans: List<GroupPlan>
) : Model {

    fun getPlan(group: Group): GroupPlan {
        return plans.find { it.group == group }.requireNotNull()
    }

    companion object : ModelProvider<AcademicPlan> {

        override val Empty: AcademicPlan = AcademicPlan(
            ModelProvider.EMPTY_ID,
            name = emptyString(),
            plans = emptyList()
        )

        val AcademicPlan.isEmpty: Boolean
            get() = this == Empty
    }

//    class Builder(
//        private val name: String,
//        plans: List<GroupPlan.Builder>
//    ) {
//
//        private val plans: MutableMap<Group, GroupPlan.Builder> = plans.associateBy { it.group }.toMutableMap()
//
//        fun add(group: Group, builder: GroupPlan.Builder.() -> Unit): Builder = apply {
//            plans[group] = GroupPlan.Builder(group, emptyMap()).apply(builder)
//        }
//
//    }

}


