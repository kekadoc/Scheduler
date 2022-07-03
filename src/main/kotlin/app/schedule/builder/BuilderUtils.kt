package app.schedule.builder

import common.extensions.same
import app.domain.model.*
import app.schedule.plan.AcademicPlan
import app.schedule.rule.Rules
import kotlin.random.Random

object BuilderUtils {

    fun build(
        plan: AcademicPlan,
        builder: ScheduleBuilder,
        rules: Rules
    ) {
        plan.getAll().forEach { (group, groupPlan) ->
            groupPlan.getAll().forEach { (discipline, disciplinePlan) ->
                val room = disciplinePlan.room
                val teacher = disciplinePlan.teacher
                if (disciplinePlan.works.values.sum() <= 0) {
                    throw IllegalStateException("Hours in empty $group $discipline")
                }
                disciplinePlan.works.filter { it.value > 0 }.forEach { (work, hours) ->

                    val availableTimeRoom = builder.getAvailableTimePoints(room)
                    val availableTimeTeacher = builder.getAvailableTimePoints(teacher)
                    val availableTime = listOf(availableTimeRoom, availableTimeTeacher)
                        .same()
                        .filter { rules.availableDays.contains(it.dayOfWeek) }
                        .toMutableList()

                    if (availableTime.isEmpty()) throw IllegalStateException("Time is not found")

                    val hoursInCycle = when (val filling = disciplinePlan.fillingType) {
                        is PlanFillingType.Evenly -> (hours.toFloat() / groupPlan.weekCount.toFloat()).coerceAtLeast(minimumValue = 1f)
                        is PlanFillingType.Limitation -> filling.limitInCycle.toFloat()
                    }
                    var hoursCounter = hoursInCycle
                    while (hoursCounter > 0) {
                        hoursCounter -= 2f
                        val timeIndex = (0 until availableTime.count()).random()
                        val time = availableTime[timeIndex]
                        availableTime.removeAt(timeIndex)
                        val lesson = Lesson(
                            id = Random.nextLong(),
                            teaching = Teaching(
                                id = Random.nextLong(),
                                discipline = discipline,
                                teacher = teacher,
                                room = room, type = work),
                            teacher = teacher,
                            room = room
                        )
                        builder.getGroup(group).getWeek(time.weekType).getDay(time.dayOfWeek).set(time.lessonNumber, lesson)
                    }
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