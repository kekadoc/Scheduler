package app.ui.schedule.create

import app.mock.Mock
import common.extensions.container
import common.view_model.ViewModel
import domain.model.*
import domain.model.schedule.GroupSettings
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

data class ScheduleCreatingState(
    val availableGroups: Map<StudentGroup, Boolean> = emptyMap(),
    val availableTeachers: Map<Teacher, Boolean> = emptyMap(),
    val availableAcademicSubjects: Map<AcademicSubject, Boolean> = emptyMap(),
    val availableStudyRooms: Map<StudyRoom, Boolean> = emptyMap(),
    val availableDays: Map<DayOfWeek, Boolean> = emptyMap(),
    val availableLessonTimes: Map<LessonTime, Boolean> = emptyMap(),
    val groupSettings: Map<StudentGroup, GroupSettings> = emptyMap()
)

val ScheduleCreatingState.studentGroups: List<StudentGroup>
    get() = availableGroups.filter { it.value }.map { it.key }

val ScheduleCreatingState.teachers: List<Teacher>
    get() = availableTeachers.filter { it.value }.map { it.key }

val ScheduleCreatingState.academicSubjects: List<AcademicSubject>
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
                    availableStudyRooms = getAllStudyRooms().associateWith { true },
                    availableDays = getAllDays().associateWith { true },
                    availableLessonTimes = getAllLessonTimes().associateWith { true },
                    groupSettings = groups.mapValues { (group, _) -> GroupSettings(group) }
                )
            }
        }
    }


    fun setGroupLessons(group: StudentGroup, lessons: Map<AcademicSubject, AcademicHour>) = intent {
        val currentGroupSettings = state.groupSettings.toMutableMap()
        val currentGroupSetting = currentGroupSettings[group] ?: GroupSettings(group)
        currentGroupSettings[group] = currentGroupSetting.copy(lessons = lessons)
        reduce { state.copy(groupSettings = currentGroupSettings) }
    }

    fun setGroupLesson(group: StudentGroup, lesson: AcademicSubject, hours: AcademicHour) = intent {
        val newGroupSettings: MutableMap<StudentGroup, GroupSettings> = state.groupSettings.toMutableMap()
        val currentGroupSetting: GroupSettings = newGroupSettings[group] ?: GroupSettings(group)
        val newGroupLessons: MutableMap<AcademicSubject, AcademicHour> = currentGroupSetting.lessons.toMutableMap().apply {
            put(lesson, hours)
        }
        val newGroupSetting = currentGroupSetting.copy(lessons = newGroupLessons)

        newGroupSettings[group] = newGroupSetting
        reduce { state.copy(groupSettings = newGroupSettings) }
    }


    private fun getAllGroups(): List<StudentGroup> = Mock.studentGroups(20)
    private fun getAllTeachers(): List<Teacher> = Mock.teachers(20)
    private fun getAllAcademicSubjects(): List<AcademicSubject> = Mock.academicSubjects(20)
    private fun getAllStudyRooms(): List<StudyRoom> = Mock.studyRooms(20)
    private fun getAllDays(): List<DayOfWeek> = Mock.dayOfWeeks(6)
    private fun getAllLessonTimes(): List<LessonTime> = Mock.lessonTimes()


    fun setAvailableGroups(availableGroups: Map<StudentGroup, Boolean>) = intent {
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

    fun setAvailableAcademicSubjects(availableAcademicSubjects: Map<AcademicSubject, Boolean>) = intent {
        reduce { state.copy(availableAcademicSubjects = availableAcademicSubjects) }
    }

    fun setAvailableStudyRooms(availableStudyRooms: Map<StudyRoom, Boolean>) = intent {
        reduce { state.copy(availableStudyRooms = availableStudyRooms) }
    }

    fun setAvailableDays(availableDays: Map<DayOfWeek, Boolean>) = intent {
        reduce { state.copy(availableDays = availableDays) }
    }

    fun setAvailableLessonTimes(availableLessonTimes: Map<LessonTime, Boolean>) = intent {
        reduce { state.copy(availableLessonTimes = availableLessonTimes) }
    }

}