package excel.model

import common.extensions.requireNotNull

typealias DayOfWeek = String

data class Schedule(
    //val groups: Map<Group, Map<DayOfWeek, List<LessonCell>>>,
    val lessons: Map<DayOfWeek, List<Map<Group, LessonCell>>>,
    val lessonsTime: List<LessonTime>,
    val dayOfWeeks: List<DayOfWeek>,
    val groups: Set<Group>
)

/*fun Schedule.fromDayOfWeek(dayOfWeek: DayOfWeek): Map<Group, List<LessonCell>> {
    return groups.mapValues { (_, days) ->
        days[dayOfWeek].requireNotNull()
    }
}

fun Schedule.dayOfWeeks(): List<DayOfWeek> {
    return groups.values.first().keys.toList()
}*/

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