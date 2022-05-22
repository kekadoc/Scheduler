package di

import data.data_source.local.space.SpaceLocalDataSource
import data.data_source.local.space.SpaceLocalDataSourceImpl
import data.data_source.local.space.preferences.SpacePreferences
import data.data_source.local.space.preferences.SpacePreferencesImpl
import org.koin.dsl.module

val localDataSourceModule = module {

    single<SpacePreferences> {
        SpacePreferencesImpl()
    }

    single<SpaceLocalDataSource> {
        SpaceLocalDataSourceImpl(
            preferences = get()
        )
    }
}