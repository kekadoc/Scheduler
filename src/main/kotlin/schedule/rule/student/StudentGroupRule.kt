package schedule.rule.student

import domain.model.DayOfWeek
import domain.model.Group
import domain.model.LessonNumber

class StudentGroupRule {

    private var groups: MutableMap<Group, Option> = mutableMapOf()

    fun getAll(): Map<Group, Option> {
        return groups
    }

    fun get(group: Group): Option {
        return groups.getOrPut(group) { Option() }
    }


    data class Option(
        var order: Int = 0,
        val availableDayOfWeeks: MutableSet<DayOfWeek> = DayOfWeek.values().dropLast(1).toMutableSet(),
        val availableLessonNumbers: MutableSet<LessonNumber> = mutableSetOf(1, 2, 3, 4, 5, 6) // TODO: 22.06.2022 Refactor
    )

}