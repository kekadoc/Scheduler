package app.mock

import domain.model.*

object Mock {

    val DISCIPLINE: Discipline = Discipline(
        id = -1L,
        name = "AcademicSubject name",
        description = "AcademicSubject description"
    )

    fun disciplines(count: Int): List<Discipline> {
        return List(count) { index ->
            Discipline(
                id = index.toLong(),
                name = "AcademicSubject #$index",
                description = "AcademicSubject Description #$index"
            )
        }
    }


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

    fun studyRooms(count: Int): List<Room> {
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


    val LESSON: Lesson = Lesson(
        id = -1,
        name = "Биология",
        description = "Description",
        teacher = TEACHER,
        room = ROOM
    )

}