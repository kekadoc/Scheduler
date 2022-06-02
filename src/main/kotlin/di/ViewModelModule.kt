package di

import app.ApplicationViewModel
import org.koin.dsl.module
import ui.database.teachers.DialogTeacherViewModel
import ui.database.teachers.TeachersViewModel

val viewModelsModule = module {

    factory {
        ApplicationViewModel(spaceRepository = get())
    }

    factory {
        TeachersViewModel(teacherLocalDataSource = get())
    }

    factory {
        DialogTeacherViewModel(teachersRepository = get())
    }

}