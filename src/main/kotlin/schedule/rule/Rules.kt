package schedule.rule

import schedule.rule.discipline.TeachingRule
import schedule.rule.room.RoomRule
import schedule.rule.student.StudentGroupRule
import schedule.rule.teacher.TeacherRule

data class Rules(
    val teacherRule: TeacherRule,
    val groupRule: StudentGroupRule,
    val roomRule: RoomRule,
    val teachingRule: TeachingRule
)
