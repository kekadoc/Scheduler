package di

import data.repository.SpaceRepository
import data.repository.SpaceRepositoryImpl
import data.repository.TeachersRepository
import data.repository.TeachersRepositoryImpl
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
}