package di

import data.repository.*
import org.koin.dsl.module

val repositoriesModule = module {

    single<SpaceRepository> {
        SpaceRepositoryImpl(
            localDataSource = get()
        )
    }

    single<TeachersRepository> {
        TeachersRepositoryImpl(
            localDataSource = get()
        )
    }

    single<StudentGroupRepository> {
        StudentGroupRepositoryImpl(
            localDataSource = get()
        )
    }

}