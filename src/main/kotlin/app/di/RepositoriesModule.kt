package app.di

import app.data.repository.discipline.DisciplinesRepository
import app.data.repository.discipline.DisciplinesRepositoryImpl
import app.data.repository.group.GroupsRepository
import app.data.repository.group.GroupsRepositoryImpl
import app.data.repository.plan.AcademicPlanRepository
import app.data.repository.plan.AcademicPlanRepositoryImpl
import app.data.repository.room.RoomsRepository
import app.data.repository.room.RoomsRepositoryImpl
import app.data.repository.teacher.TeachersRepository
import app.data.repository.teacher.TeachersRepositoryImpl
import org.koin.dsl.module

val repositoriesModule = module {

    single<TeachersRepository> {
        TeachersRepositoryImpl(
            localDataSource = get(),
            converter = get()
        )
    }

    single<GroupsRepository> {
        GroupsRepositoryImpl(
            localDataSource = get(),
            converter = get()
        )
    }

    single<RoomsRepository> {
        RoomsRepositoryImpl(
            localDataSource = get(),
            converter = get()
        )
    }

    single<DisciplinesRepository> {
        DisciplinesRepositoryImpl(
            localDataSource = get(),
            converter = get(),
            teacherLocalDataSource = get(),
            roomsLocalDataSource = get()
        )
    }

}

val academicPlanRepositoryModule = module {

    single<AcademicPlanRepository> {
        AcademicPlanRepositoryImpl()
    }
}