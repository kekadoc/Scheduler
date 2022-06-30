package schedule.builder

import common.extensions.requireNotNull
import common.logger.Logger
import domain.model.*

class ScheduleBuilder(
    val maxLessonsInDay: Int,
    val groups: Set<Group>
) {

    private val schedules: Map<Group, GroupSchedule> = groups.associateWith { GroupSchedule() }

    fun getGroup(group: Group): GroupSchedule {
        Logger.log("group=$group; groups=${schedules.keys}")
        return schedules[group].requireNotNull()
    }

    fun getAll(): Map<Group, GroupSchedule> {
        return schedules
    }


    class GroupSchedule {

        private val firstWeek: WeekSchedule = WeekSchedule()
        private val secondWeek: WeekSchedule = WeekSchedule()

        fun getWeek(weekType: WeekType): WeekSchedule {
            return when (weekType) {
                WeekType.FIRST -> firstWeek
                WeekType.SECOND -> secondWeek
            }
        }
    }

    class WeekSchedule {

        private val schedule = java.util.LinkedHashMap<DayOfWeek, DaySchedule>(
            DayOfWeek.values().size
        ).apply {
            DayOfWeek.values().forEach { dayOfWeek ->
                put(dayOfWeek, DaySchedule())
            }
        }

        fun getDay(dayOfWeek: DayOfWeek): DaySchedule {
            return schedule[dayOfWeek].requireNotNull()
        }

    }

    class DaySchedule {

        private val lessons = LinkedHashMap<LessonNumber, Lesson?>(MAX_LESSONS_IN_DAY).apply {
            repeat(MAX_LESSONS_IN_DAY) { number -> put(number, null) }
        }

        fun set(number: LessonNumber, lesson: Lesson?) {
            lessons[number] = lesson
        }

        fun get(number: LessonNumber): Lesson? {
            return lessons[number]
        }

        fun getAll(): Map<LessonNumber, Lesson?> {
            return lessons
        }

        companion object {
            const val MAX_LESSONS_IN_DAY = 6
        }

    }

}