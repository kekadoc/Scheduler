package schedule.rule

import domain.model.DayOfWeek
import schedule.rule.room.RoomRule
import schedule.rule.student.StudentGroupRule
import schedule.rule.teacher.TeacherRule

data class Rules(
    val availableDays: Set<DayOfWeek>,
    val teacherRule: TeacherRule,
    val groupRule: StudentGroupRule,
    val roomRule: RoomRule,
    //val teachingRule: TeachingRule
)
