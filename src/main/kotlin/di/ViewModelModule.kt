package di

import app.ApplicationViewModel
import app.ui.database.discipline.DisciplinesViewModel
import app.ui.database.teachers.DialogTeacherViewModel
import app.ui.database.teachers.TeachersViewModel
import app.ui.schedule.create.ScheduleCreatingViewModel
import app.ui.schedule.create.plan.AcademicPlanViewModel
import common.view_model.ViewModelStore
import org.koin.dsl.module

val viewModelsModule = module {

    single<ViewModelStore> { ViewModelStore() }

    factory {
        ApplicationViewModel(spaceRepository = get())
    }

    factory {
        TeachersViewModel(teacherLocalDataSource = get())
    }

    factory {
        DisciplinesViewModel(localDataSource = get())
    }

    factory {
        DialogTeacherViewModel(teachersRepository = get())
    }

    factory {
        ScheduleCreatingViewModel()
    }

    factory {
        AcademicPlanViewModel()
    }

}