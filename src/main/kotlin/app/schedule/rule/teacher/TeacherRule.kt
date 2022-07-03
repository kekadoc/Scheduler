package app.schedule.rule.teacher

import app.domain.model.DayOfWeek
import app.domain.model.LessonNumber
import app.domain.model.Teacher

class TeacherRule {

    private var teachers: MutableMap<Teacher, Option> = mutableMapOf()

    fun getAll(): Map<Teacher, Option> {
        return teachers
    }

    fun get(group: Teacher): Option {
        return teachers.getOrPut(group) { Option() }
    }


    data class Option(
        var order: Int = 0,
        val availableDayOfWeeks: MutableSet<DayOfWeek> = DayOfWeek.values().dropLast(1).toMutableSet(),
        val availableLessonNumbers: MutableSet<LessonNumber> = mutableSetOf(1, 2, 3, 4, 5, 6) // TODO: 22.06.2022 Refactor
    )


}