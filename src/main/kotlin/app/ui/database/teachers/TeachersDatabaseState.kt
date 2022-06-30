package app.ui.database.teachers

import domain.model.Teacher

data class TeachersDatabaseState(
    val teachers: List<Teacher> = emptyList(),
    val isLoading: Boolean = false
)