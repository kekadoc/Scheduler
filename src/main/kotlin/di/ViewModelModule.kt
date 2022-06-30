package di

import app.ApplicationViewModel
import app.ui.database.discipline.DisciplinesViewModel
import app.ui.database.group.GroupsDatabaseViewModel
import app.ui.database.rooms.RoomsDatabaseViewModel
import app.ui.database.teachers.TeachersDatabaseViewModel
import app.ui.schedule.create.ScheduleCreatingViewModel
import app.ui.schedule.create.plan.AcademicPlanViewModel
import common.view_model.ViewModelStore
import injector.Inject
import org.koin.dsl.module
import schedule.plan.AcademicPlan

val viewModelsModule = module {

    single<ViewModelStore> { ViewModelStore() }

    single {
        AcademicPlan()
    }

    single {
        Inject(
            disciplineRepository = get(),
            teachersRepository = get(),
            roomRepository = get(),
            groupRepository = get(),
            academicPlan = get()
        )
    }

    factory {
        ApplicationViewModel(
            spaceRepository = get(),
            injector = get(),
            teachersRepository = get(),
            groupRepository = get(),
            roomRepository = get(),
            disciplineRepository = get()
        )
    }

    //Database ViewModels
    factory {
        TeachersDatabaseViewModel(teachersRepository = get())
    }
    factory {
        RoomsDatabaseViewModel(roomRepository = get())
    }
    factory {
        DisciplinesViewModel(disciplineRepository = get(), teachersRepository = get(), roomRepository = get())
    }
    factory {
        GroupsDatabaseViewModel(groupRepository = get())
    }


    factory {
        ScheduleCreatingViewModel()
    }

    factory {
        AcademicPlanViewModel(groupRepository = get(), academicPlan = get())
    }

}