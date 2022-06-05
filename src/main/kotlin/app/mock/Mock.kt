package app.mock

import domain.model.AcademicSubject
import domain.model.StudentGroup

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
}