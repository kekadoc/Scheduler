package app.mock

import domain.model.*

object Mock {

    val academicSubject: AcademicSubject = AcademicSubject(
        id = -1L,
        name = "AcademicSubject name",
        description = "AcademicSubject description"
    )

    fun academicSubjects(count: Int): List<AcademicSubject> {
        return List(count) { index ->
            AcademicSubject(
                id = index.toLong(),
                name = "AcademicSubject #$index",
                description = "AcademicSubject Description #$index"
            )
        }
    }


    val studentGroup: StudentGroup = StudentGroup(
        id = -1L,
        name = "StudentGroup"
    )

    fun studentGroups(count: Int): List<StudentGroup> {
        return List(count) { index -> StudentGroup(index.toLong(), "StudentGroup #$index") }
    }


    val teacher: Teacher = Teacher(
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


    val studyRoom: StudyRoom = StudyRoom(
        id = -1L,
        name = "StudyRoom",
        description = "StudyRoom Desc"
    )

    fun studyRooms(count: Int): List<StudyRoom> {
        return List(count) { index ->
            StudyRoom(
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

}