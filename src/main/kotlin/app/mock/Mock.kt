package app.mock

import app.domain.model.*
import app.schedule.plan.DisciplinePlan
import app.schedule.plan.GroupPlan

object Mock {

    val GROUP: Group = Group(
        id = -1L,
        name = "StudentGroup"
    )

    fun studentGroups(count: Int): List<Group> {
        return List(count) { index -> Group(index.toLong(), "StudentGroup #$index") }
    }


    val TEACHER: Teacher = Teacher(
        id = -1L,
        firstName = "Иванов",
        middleName = "Иванович",
        lastName = "Иван",
        speciality = "Преподаватель"
    )

    fun teachers(count: Int): List<Teacher> {
        return List(count) { index ->
            Teacher(
                id = index.toLong(),
                firstName = "Имя $index",
                lastName = "Фамилия $index",
                middleName = "Отчество $index",
                speciality = "Должность $index"
            )
        }
    }


    val ROOM: Room = Room(
        id = -1L,
        name = "StudyRoom",
        description = "StudyRoom Desc"
    )

    fun rooms(count: Int): List<Room> {
        return List(count) { index ->
            Room(
                id = index.toLong(),
                name = "StudyRoom $index",
                description = "StudyRoom Desc $index"
            )
        }
    }

    fun dayOfWeeks(count: Int): List<DayOfWeek> {
        return DayOfWeek.values().take(count)
    }

    fun lessonTimes(): List<LessonTime> {
        return listOf(
            LessonTime(id = 1L, range = "8:30" to "10:00"),
            LessonTime(id = 2L, range = "10:10" to "11:30"),
            LessonTime(id = 3L, range = "12:00" to "13:30"),
            LessonTime(id = 4L, range = "13:45" to "15:15"),
            LessonTime(id = 5L, range = "15:25" to "16:65"),
            LessonTime(id = 6L, range = "17:05" to "18:30")
        )
    }


    val DISCIPLINE: Discipline = Discipline(
        id = -1L,
        name = "AcademicSubject name",
        teachers = listOf(TEACHER),
        rooms = listOf(ROOM)
    )

    fun disciplines(count: Int): List<Discipline> {
        return List(count) { index ->
            Discipline(
                id = index.toLong(),
                name = "AcademicSubject #$index",
                teachers = listOf(TEACHER),
                rooms = listOf(ROOM)
            )
        }
    }


    val TEACHING: Teaching = Teaching(
        id = -1,
        discipline = DISCIPLINE,
        teacher = TEACHER,
        room = ROOM
    )

    val LESSON: Lesson = Lesson(
        id = -1,
        teaching = TEACHING,
        teacher = TEACHER,
        room = ROOM
    )

    fun teachings(count: Int): List<Teaching> {
        return List(count) { index ->
            Teaching(
                id = index.toLong(),
                discipline = DISCIPLINE.copy(name = "Discipline #$index"),
                teacher = TEACHER,
                room = ROOM
            )
        }
    }


    val DISCIPLINE_PLAN = DisciplinePlan(
        discipline = DISCIPLINE,
        works = mapOf(
            WorkType.LECTURE to 8,
            WorkType.PRACTICE to 16,
        )
    )


    fun groupPlans(count: Int): List<GroupPlan> {
        val groups = studentGroups(count)
        return List(count) {
            GroupPlan(
                group = groups[it],
                weekCount = 18,
                items = disciplinePlans((3..5).random())
            )
        }
    }

    fun disciplinePlans(count: Int): List<DisciplinePlan> {
        val disciplines = disciplines(count)
        return List(count) { index ->
            DisciplinePlan(
                disciplines[index],
                works = List((0..WorkType.values().count()).random()) {
                    WorkType.values().random() to (0..30).random()
                }.toMap()
            )
        }
    }

    val GROUP_PLAN = GroupPlan(
        group = GROUP,
        weekCount = 18,
        items = mutableListOf(
            DISCIPLINE_PLAN
        )
    )

}