package di

import app.ApplicationViewModel
import app.ui.database.discipline.DialogTeacherViewModel
import app.ui.database.discipline.DisciplinesViewModel
import app.ui.database.rooms.RoomsDatabaseViewModel
import app.ui.database.teachers.TeachersDatabaseViewModel
import app.ui.schedule.create.ScheduleCreatingViewModel
import app.ui.schedule.create.plan.AcademicPlanViewModel
import common.view_model.ViewModelStore
import org.koin.dsl.module

val viewModelsModule = module {

    single<ViewModelStore> { ViewModelStore() }

    factory {
        ApplicationViewModel(spaceRepository = get())
    }

    //Database ViewModels
    factory {
        TeachersDatabaseViewModel(teachersRepository = get())
    }
    factory {
        RoomsDatabaseViewModel(roomRepository = get())
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