package excel.model

import app.domain.model.WeekType
import app.schedule.builder.ScheduleBuilder
import common.extensions.requireNotNull

typealias DayOfWeek = String

data class Schedule(
    //val groups: Map<Group, Map<DayOfWeek, List<LessonCell>>>,
    val lessons: Map<DayOfWeek, List<Map<Group, LessonCell>>>,
    val lessonsTime: List<LessonTime>,
    val dayOfWeeks: List<DayOfWeek>,
    val groups: Set<Group>
)

fun ScheduleBuilder.buildExcelModel(): Schedule {
    val dayOfWeeks2: List<app.domain.model.DayOfWeek> = app.domain.model.DayOfWeek.values().toList()

    val lessonsTime: List<LessonTime> = listOf(
        "8:30-10:00",
        "10:10-11:30",
        "12:00-13:30",
        "13:45-15:15",
        "15:25-16:65",
        "17:05-18:30"
    )
    val groups: Set<Group> = this.groups.map { Group(it.name) }.toSet()
    val lessons: Map<DayOfWeek, List<Map<Group, LessonCell>>> = dayOfWeeks2.associateWith { dayOfWeek ->
        val groupSchedules: Map<app.domain.model.Group, ScheduleBuilder.GroupSchedule> = this.groups.associateWith { getGroup(it) }
        List(maxLessonsInDay) { lessonNumber ->
            groupSchedules.mapValues { (_, groupSchedule) ->
                val first: app.domain.model.Lesson? = groupSchedule.getWeek(WeekType.FIRST).getDay(dayOfWeek).get(lessonNumber)
                val second: app.domain.model.Lesson? = groupSchedule.getWeek(WeekType.SECOND).getDay(dayOfWeek).get(lessonNumber)
                LessonCell(
                    lessonsTime[lessonNumber],
                    first?.let {
                        Lesson(
                            name = it.name,
                            type = it.type,
                            teacherAbout = it.teacher.lastName,
                            teacherName = it.teacher.speciality,
                            room = it.room.name
                        )
                    },
                    second?.let {
                        Lesson(
                            name = it.name,
                            type = it.type,
                            teacherAbout = it.teacher.lastName,
                            teacherName = it.teacher.speciality,
                            room = it.room.name
                        )
                    },
                )
            }.mapKeys { Group(it.key.name) }
        }
    }.mapKeys { it.key.text }
    val dayOfWeeks: List<DayOfWeek> = lessons.keys.toList()

    return Schedule(
        dayOfWeeks = dayOfWeeks,
        lessonsTime = lessonsTime,
        groups = groups,
        lessons = lessons
    )
}

fun Schedule.iterate(dayOfWeek: DayOfWeek, block: (LessonIndex, LessonTime, List<Pair<Group, LessonCell>>) -> Unit) {
    lessons[dayOfWeek].requireNotNull().forEachIndexed { index, groups: Map<Group, LessonCell> ->
        block(index, lessonsTime[index], groups.toList())
    }
}

typealias LessonIndex = Int
typealias LessonTime = String

data class Group(val name: String)

data class Lesson(
    val name: String,
    val type: String,
    val teacherAbout: String,
    val teacherName: String,
    val room: String
)

data class LessonCell(
    val time: String,
    val first: Lesson?,
    val second: Lesson?
)