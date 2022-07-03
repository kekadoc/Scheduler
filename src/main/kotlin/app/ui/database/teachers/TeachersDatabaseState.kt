package app.ui.database.teachers

import app.domain.model.Teacher

data class TeachersDatabaseState(
    val teachers: List<Teacher> = emptyList(),
    val isLoading: Boolean = false
)