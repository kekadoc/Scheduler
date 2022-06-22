package schedule.builder

import common.extensions.requireNotNull
import common.extensions.same
import domain.model.*
import schedule.plan.AcademicPlan
import schedule.rule.Rules
import kotlin.math.floor
import kotlin.random.Random

object BuilderUtils {

    fun build(
        plan: AcademicPlan,
        builder: ScheduleBuilder,
        rules: Rules
    ) {
        println("build plan=${plan.getAll().size}")
        plan.getAll().forEach { (group, groupPlan) ->
            groupPlan.getAll().forEach { (teaching, hours) ->
                val teachingOption = rules.teachingRule.get(teaching)
                val requireRoom = teachingOption.room.requireNotNull()
                val requireTeacher = teachingOption.teacher.requireNotNull()
                println("HOURS=$hours")
                val hoursInCycle = when (val filling = teachingOption.planFillingType) {
                    is PlanFillingType.Evenly -> (hours.toFloat() / groupPlan.weekCount.toFloat()).coerceAtLeast(1f)
                    is PlanFillingType.Limitation -> filling.limitInCycle.toFloat()
                }

                val hoursInFirstWeek = floor(hoursInCycle / 2f).toInt()
                val hoursInSecondWeek = (hoursInCycle - hoursInFirstWeek).toInt()
                println("h=$hoursInFirstWeek hh=$hoursInSecondWeek ${hoursInCycle}")
                repeat(hoursInFirstWeek) {
                    val availablePoints = listOf(
                        builder.getAvailableTimePoints(requireRoom, WeekType.FIRST),
                        builder.getAvailableTimePoints(requireTeacher, WeekType.FIRST),
                        builder.getAvailableTimePoints(group, WeekType.FIRST)
                    ).same()
                    val first = availablePoints.firstOrNull() ?: throw IllegalStateException("Point not found for group=$group")
                    val lesson = Lesson(
                        id = Random.nextLong(),
                        teaching,
                        requireTeacher,
                        requireRoom
                    )
                    builder.getGroup(group).getWeek(WeekType.FIRST).getDay(first.dayOfWeek).set(first.lessonNumber, lesson)
                }

                repeat(hoursInSecondWeek) {
                    val availablePoints = listOf(
                        builder.getAvailableTimePoints(requireRoom, WeekType.SECOND),
                        builder.getAvailableTimePoints(requireTeacher, WeekType.SECOND),
                        builder.getAvailableTimePoints(group, WeekType.SECOND)
                    ).same()
                    val first = availablePoints.firstOrNull() ?: throw IllegalStateException("Point not found for group=$group")
                    val lesson = Lesson(
                        id = Random.nextLong(),
                        teaching,
                        requireTeacher,
                        requireRoom
                    )
                    builder.getGroup(group).getWeek(WeekType.SECOND).getDay(first.dayOfWeek).set(first.lessonNumber, lesson)
                }
            }
        }
    }

    inline fun foreach(
        scheduleBuilder: ScheduleBuilder,
        block: (WeekType, DayOfWeek, LessonNumber, Map<Group, Lesson?>) -> Unit
    ) {
        WeekType.values().forEach { weekType ->
            DayOfWeek.values().forEach { dayOfWeek ->
                repeat(scheduleBuilder.maxLessonsInDay) { lessonNumber ->
                    val groups: Map<Group, Lesson?> = scheduleBuilder.getAll()
                        .map { (group, schedule) ->
                            group to schedule.getWeek(weekType).getDay(dayOfWeek).get(lessonNumber)
                        }
                        .toMap()
                    block(weekType, dayOfWeek, lessonNumber, groups)
                }
            }
        }
    }
}