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

}


