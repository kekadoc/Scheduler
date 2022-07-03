package app.schedule.builder

import app.domain.model.*
import app.schedule.rule.student.StudentGroupRule
import app.schedule.rule.teacher.TeacherRule

fun ScheduleBuilder.getAvailableDays(
    group: Group,
    weekType: WeekType,
    groupRule: StudentGroupRule
): List<DayOfWeek> {
    val option = groupRule.get(group)
    return option.availableDayOfWeeks.filter { day ->
        getLessonsListFor(group, day, weekType).any { (number, lesson) ->
            //День считается доступным если есть пустые ячейки для предмета и эта ячейка соблюдает правила для группы
            lesson == null && option.availableLessonNumbers.contains(number)
        }
    }
}

fun ScheduleBuilder.getAvailableDays(
    teacher: Teacher,
    weekType: WeekType,
    teacherRule: TeacherRule
): List<DayOfWeek> {
    val option = teacherRule.get(teacher)
    return option.availableDayOfWeeks.filter { day ->
        getLessonsListFor(teacher, day, weekType).any { (number, lesson) ->
            //День считается доступным если есть пустые ячейки для предмета и эта ячейка соблюдает правила для учителя
            lesson == null && option.availableLessonNumbers.contains(number)
        }
    }
}

/**
 * Получить список уроков на указанный день для указанного учителя для указанной недели
 */
fun ScheduleBuilder.getLessonsListFor(
    teacher: Teacher,
    dayOfWeek: DayOfWeek,
    weekType: WeekType
): Map<LessonNumber, Lesson?> {
    val lessons: MutableMap<LessonNumber, Lesson?> = List<Lesson?>(ScheduleBuilder.DaySchedule.MAX_LESSONS_IN_DAY) { null }
        .mapIndexed { index, nothing -> index to nothing }
        .toMap()
        .toMutableMap()
    //Только указанная неделя
    val weeks = getAll().values.map { it.getWeek(weekType) }
    //Только указанные дни
    val days = weeks.map { it.getDay(dayOfWeek) }
    days.forEach { day ->
        day.getAll().forEach { (number, lesson) ->
            if (lesson != null && lesson.teacher == teacher) lessons[number] = lesson
        }
    }
    return lessons
}

/**
 * Получить список уроков на указанный день для указанного учителя для указанной недели
 */
fun ScheduleBuilder.getLessonsListFor(
    group: Group,
    dayOfWeek: DayOfWeek,
    weekType: WeekType
): Map<LessonNumber, Lesson?> {
    return getGroup(group).getWeek(weekType).getDay(dayOfWeek).getAll()
}

data class TimePoint(
    val weekType: WeekType,
    val dayOfWeek: DayOfWeek,
    val lessonNumber: LessonNumber
)

fun ScheduleBuilder.getAvailableTimePoints(teacher: Teacher, weekType: WeekType? = null): List<TimePoint> {
    val points = mutableListOf<TimePoint>()
    BuilderUtils.foreach(this) { type, dayOfWeek, number, groups ->
        if (weekType != null && type != weekType) return@foreach
        if (!groups.any { it.value?.teacher == teacher }) points.add(TimePoint(type, dayOfWeek, number))
    }
    return points
}

fun ScheduleBuilder.getAvailableTimePoints(room: Room, weekType: WeekType? = null): List<TimePoint> {
    val points = mutableListOf<TimePoint>()
    BuilderUtils.foreach(this) { type, dayOfWeek, number, groups ->
        if (weekType != null && type != weekType) return@foreach
        if (!groups.any { it.value?.room == room }) points.add(TimePoint(type, dayOfWeek, number))
    }
    return points
}

fun ScheduleBuilder.getAvailableTimePoints(group: Group, weekType: WeekType? = null): List<TimePoint> {
    val points = mutableListOf<TimePoint>()
    WeekType.values().forEach { type ->
        if (weekType != null && type != weekType) return@forEach
        DayOfWeek.values().forEach { day ->
            getGroup(group).getWeek(type).getDay(day).getAll().forEach { (number, lesson) ->
                if (lesson == null ) {
                    points.add(TimePoint(type, day, number))
                }
            }
        }

    }
    return points
}