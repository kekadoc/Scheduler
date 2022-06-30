package app.ui.schedule.create

import common.extensions.container
import common.view_model.ViewModel
import domain.model.Group
import excel.CreateScheduleXLSX
import excel.model.buildExcelModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import schedule.builder.BuilderUtils
import schedule.builder.ScheduleBuilder
import schedule.plan.AcademicPlan
import schedule.plan.GroupPlan
import schedule.rule.Rules
import schedule.rule.room.RoomRule
import schedule.rule.student.StudentGroupRule
import schedule.rule.teacher.TeacherRule

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
            // teachingRule = TeachingRule()
        )
        BuilderUtils.build(academicPlan, scheduleBuilder, rules)
        val scheduleExcel = scheduleBuilder.buildExcelModel()
        CreateScheduleXLSX.create(scheduleExcel)
    }

}