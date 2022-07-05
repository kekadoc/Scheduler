package app.di

import app.ApplicationViewModel
import app.ui.database.discipline.DisciplinesViewModel
import app.ui.database.group.GroupsDatabaseViewModel
import app.ui.database.plan.AcademicPlanViewModel
import app.ui.database.rooms.RoomsDatabaseViewModel
import app.ui.database.teachers.TeachersDatabaseViewModel
import app.ui.schedule.create.ScheduleCreatingViewModel
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
        ScheduleCreatingViewModel(planRepository = get())
    }
    factory {
        AcademicPlanViewModel(
            groupsRepository = get(),
            academicPlanRepository = get(),
            teachersRepository = get(),
            roomsRepository = get(),
            disciplinesRepository = get()
        )
    }

}