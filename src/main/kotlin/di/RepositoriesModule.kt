package di

import data.repository.group.GroupRepository
import data.repository.group.GroupRepositoryImpl
import data.repository.room.RoomRepository
import data.repository.room.RoomRepositoryImpl
import data.repository.space.SpaceRepository
import data.repository.space.SpaceRepositoryImpl
import data.repository.teacher.TeachersRepository
import data.repository.teacher.TeachersRepositoryImpl
import org.koin.dsl.module

val repositoriesModule = module {

    single<SpaceRepository> {
        SpaceRepositoryImpl(
            localDataSource = get()
        )
    }

    single<TeachersRepository> {
        TeachersRepositoryImpl(
            localDataSource = get(),
            converter = get()
        )
    }

    single<GroupRepository> {
        GroupRepositoryImpl(
            localDataSource = get(),
            converter = get()
        )
    }

    single<RoomRepository> {
        RoomRepositoryImpl(
            localDataSource = get(),
            converter = get()
        )
    }

}