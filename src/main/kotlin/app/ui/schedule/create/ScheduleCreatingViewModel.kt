package app.ui.schedule.create

import common.extensions.container
import common.view_model.ViewModel
import app.domain.model.DayOfWeek
import app.domain.model.Group
import excel.CreateScheduleXLSX
import excel.model.buildExcelModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import app.schedule.builder.BuilderUtils
import app.schedule.builder.ScheduleBuilder
import app.schedule.plan.AcademicPlan
import app.schedule.plan.GroupPlan
import app.schedule.rule.Rules
import app.schedule.rule.room.RoomRule
import app.schedule.rule.student.StudentGroupRule
import app.schedule.rule.teacher.TeacherRule

class ScheduleCreatingState

class ScheduleCreatingViewModel : ViewModel(), ContainerHost<ScheduleCreatingState, Unit> {

    override val container = container<ScheduleCreatingState, Unit>(ScheduleCreatingState())

    fun buildSchedule(plan: Map<Group, GroupPlan>) = intent {
        val availableGroups = plan.keys
        val maxLessonsInDay = 6 // TODO: 24.06.2022 Mock
        val scheduleBuilder = ScheduleBuilder(maxLessonsInDay, availableGroups)
        val academicPlan = AcademicPlan(plan)
        val rules = Rules(
            teacherRule = TeacherRule(),
            groupRule = StudentGroupRule(),
            roomRule = RoomRule(),
            availableDays = DayOfWeek.values().take(6).toSet()
            // teachingRule = TeachingRule()
        )
        BuilderUtils.build(academicPlan, scheduleBuilder, rules)
        val scheduleExcel = scheduleBuilder.buildExcelModel()
        CreateScheduleXLSX.create(scheduleExcel)
    }

}