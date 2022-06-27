package app.ui.schedule.create

import app.mock.Mock
import common.extensions.container
import common.view_model.ViewModel
import domain.model.*
import domain.model.schedule.GroupSettings
import excel.CreateScheduleXLSX
import excel.model.buildExcelModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import schedule.builder.BuilderUtils
import schedule.builder.ScheduleBuilder
import schedule.plan.AcademicPlan
import schedule.plan.GroupPlan
import schedule.rule.Rules
import schedule.rule.room.RoomRule
import schedule.rule.student.StudentGroupRule
import schedule.rule.teacher.TeacherRule

data class ScheduleCreatingState(
    val availableGroups: Map<Group, Boolean> = emptyMap(),
    val availableTeachers: Map<Teacher, Boolean> = emptyMap(),
    val availableAcademicSubjects: Map<Discipline, Boolean> = emptyMap(),
    val availableRooms: Map<Room, Boolean> = emptyMap(),
    val availableDays: Map<DayOfWeek, Boolean> = emptyMap(),
    val availableLessonTimes: Map<LessonTime, Boolean> = emptyMap(),
    val groupSettings: Map<Group, GroupSettings> = emptyMap()
)

val ScheduleCreatingState.groups: List<Group>
    get() = availableGroups.filter { it.value }.map { it.key }

val ScheduleCreatingState.teachers: List<Teacher>
    get() = availableTeachers.filter { it.value }.map { it.key }

val ScheduleCreatingState.disciplines: List<Discipline>
    get() = availableAcademicSubjects.filter { it.value }.map { it.key }


class ScheduleCreatingViewModel : ViewModel(), ContainerHost<ScheduleCreatingState, Unit> {

    override val container = container<ScheduleCreatingState, Unit>(ScheduleCreatingState())


    init {
        intent {
            val groups = getAllGroups().associateWith { true }
            reduce {
                state.copy(
                    availableGroups = groups,
                    availableTeachers = getAllTeachers().associateWith { true },
                    availableAcademicSubjects = getAllAcademicSubjects().associateWith { true },
                    availableRooms = getAllStudyRooms().associateWith { true },
                    availableDays = getAllDays().associateWith { true },
                    availableLessonTimes = getAllLessonTimes().associateWith { true },
                    groupSettings = groups.mapValues { (group, _) -> GroupSettings(group) }
                )
            }
        }
    }

    fun buildSchedule(plan: Map<Group, GroupPlan>, availableGroups: Set<Group>) = intent {
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
        println(scheduleExcel)
        CreateScheduleXLSX.create(scheduleExcel)
    }


    fun setGroupLessons(group: Group, lessons: Map<Discipline, AcademicHour>) = intent {
        val currentGroupSettings = state.groupSettings.toMutableMap()
        val currentGroupSetting = currentGroupSettings[group] ?: GroupSettings(group)
        currentGroupSettings[group] = currentGroupSetting.copy(lessons = lessons)
        reduce { state.copy(groupSettings = currentGroupSettings) }
    }

    fun setGroupLesson(group: Group, lesson: Discipline, hours: AcademicHour) = intent {
        val newGroupSettings: MutableMap<Group, GroupSettings> = state.groupSettings.toMutableMap()
        val currentGroupSetting: GroupSettings = newGroupSettings[group] ?: GroupSettings(group)
        val newGroupLessons: MutableMap<Discipline, AcademicHour> = currentGroupSetting.lessons.toMutableMap().apply {
            put(lesson, hours)
        }
        val newGroupSetting = currentGroupSetting.copy(lessons = newGroupLessons)

        newGroupSettings[group] = newGroupSetting
        reduce { state.copy(groupSettings = newGroupSettings) }
    }


    private fun getAllGroups(): List<Group> = Mock.studentGroups(20)
    private fun getAllTeachers(): List<Teacher> = Mock.teachers(20)
    private fun getAllAcademicSubjects(): List<Discipline> = Mock.disciplines(20)
    private fun getAllStudyRooms(): List<Room> = Mock.rooms(20)
    private fun getAllDays(): List<DayOfWeek> = Mock.dayOfWeeks(6)
    private fun getAllLessonTimes(): List<LessonTime> = Mock.lessonTimes()


    fun setAvailableGroups(availableGroups: Map<Group, Boolean>) = intent {
        reduce {
            state.copy(
                availableGroups = availableGroups,
                groupSettings = availableGroups.mapValues { (group, _) -> GroupSettings(group) }
            )
        }
    }

    fun setAvailableTeachers(availableTeachers: Map<Teacher, Boolean>) = intent {
        reduce { state.copy(availableTeachers = availableTeachers) }
    }

    fun setAvailableAcademicSubjects(availableAcademicSubjects: Map<Discipline, Boolean>) = intent {
        reduce { state.copy(availableAcademicSubjects = availableAcademicSubjects) }
    }

    fun setAvailableStudyRooms(availableRooms: Map<Room, Boolean>) = intent {
        reduce { state.copy(availableRooms = availableRooms) }
    }

    fun setAvailableDays(availableDays: Map<DayOfWeek, Boolean>) = intent {
        reduce { state.copy(availableDays = availableDays) }
    }

    fun setAvailableLessonTimes(availableLessonTimes: Map<LessonTime, Boolean>) = intent {
        reduce { state.copy(availableLessonTimes = availableLessonTimes) }
    }

}