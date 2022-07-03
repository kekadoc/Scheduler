package app.di

import app.data.data_source.local.unit.space.SpaceLocalDataSource
import app.data.data_source.local.unit.space.SpaceLocalDataSourceImpl
import app.data.data_source.local.unit.space.preferences.SpacePreferences
import app.data.data_source.local.unit.space.preferences.SpacePreferencesImpl
import app.data.repository.space.SpacesRepository
import app.data.repository.space.SpacesRepositoryImpl
import org.koin.dsl.module

val spacesModule = module {

    single<SpacePreferences> {
        SpacePreferencesImpl()
    }

    single<SpaceLocalDataSource> {
        SpaceLocalDataSourceImpl(
            preferences = get()
        )
    }

    single<SpacesRepository> {
        SpacesRepositoryImpl(
            localDataSource = get()
        )
    }

}