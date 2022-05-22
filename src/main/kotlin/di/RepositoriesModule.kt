package di

import data.repository.SpaceRepository
import data.repository.SpaceRepositoryImpl
import org.koin.dsl.module

val repositoriesModule = module {

    single<SpaceRepository> {
        SpaceRepositoryImpl(
            localDataSource = get()
        )
    }
}