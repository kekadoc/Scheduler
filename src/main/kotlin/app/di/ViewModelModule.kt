package app.di

import app.ApplicationViewModel
import app.ui.database.discipline.DisciplinesViewModel
import app.ui.database.group.GroupsDatabaseViewModel
import app.ui.database.rooms.RoomsDatabaseViewModel
import app.ui.database.teachers.TeachersDatabaseViewModel
import app.ui.schedule.create.ScheduleCreatingViewModel
import app.ui.schedule.create.plan.AcademicPlanViewModel
import common.view_model.ViewModelStore
import org.koin.dsl.module

val viewModelsModule = module {

    //ViewModelStore
    single { ViewModelStore() }

    //Application
    factory {
        ApplicationViewModel(
            viewModelStore = get(),
            spacesRepository = get(),
            injection = get()
        )
    }

    //Database ViewModels
    factory {
        TeachersDatabaseViewModel(teachersRepository = get())
    }
    factory {
        RoomsDatabaseViewModel(roomsRepository = get())
    }
    factory {
        DisciplinesViewModel(disciplinesRepository = get(), teachersRepository = get(), roomsRepository = get())
    }
    factory {
        GroupsDatabaseViewModel(groupsRepository = get())
    }

    //Schedule creating ViewModels
    factory {
        ScheduleCreatingViewModel()
    }
    factory {
        AcademicPlanViewModel(groupsRepository = get(), academicPlanRepository = get())
    }

}