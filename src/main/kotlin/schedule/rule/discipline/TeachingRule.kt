package schedule.rule.discipline

import domain.model.PlanFillingType
import domain.model.Teaching

class TeachingRule {

    private val options = mutableMapOf<Teaching, Option>()


    fun add(teaching: Teaching, option: Option) {
        options[teaching] = option
    }

    fun get(teaching: Teaching): Option {
        return options.getOrPut(teaching) { Option() }
    }

    data class Option(
        var order: Int = 0,
        var planFillingType: PlanFillingType = PlanFillingType.Evenly
    )

}