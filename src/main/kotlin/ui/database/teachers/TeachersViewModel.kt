package ui.database.teachers

import common.view_model.ViewModel
import data.data_source.local.unit.teacher.TeacherLocalDataSource
import domain.model.Teacher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

// TODO: 02.06.2022
class TeachersViewModel(
    private val teacherLocalDataSource: TeacherLocalDataSource
) : ViewModel() {

    val all: Flow<List<Teacher>>
        get() = flowOf(
            listOf(
                Teacher(id = 0, firstName = "Иван", middleName = "Иванович", lastName = "Иванов", speciality = "Старший преподаватель"),
                Teacher(id = 1, firstName = "Алексей", middleName = "Петрович", lastName = "Кус", speciality = "Лаборант"),
                Teacher(id = 3, firstName = "Юлия", middleName = "Сергеевна", lastName = "Лолина", speciality = "Кандидат математических наук"),
            )
        )//teacherLocalDataSource.data

}