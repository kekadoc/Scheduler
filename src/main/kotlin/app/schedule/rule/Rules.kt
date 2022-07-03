package app.schedule.rule

import app.domain.model.DayOfWeek
import app.schedule.rule.room.RoomRule
import app.schedule.rule.student.StudentGroupRule
import app.schedule.rule.teacher.TeacherRule

data class Rules(
    val availableDays: Set<DayOfWeek>,
    val teacherRule: TeacherRule,
    val groupRule: StudentGroupRule,
    val roomRule: RoomRule,
    //val teachingRule: TeachingRule
)
