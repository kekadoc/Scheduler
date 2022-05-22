package di

import app.ApplicationViewModel
import org.koin.dsl.module

val viewModelsModule = module {
    single {
        ApplicationViewModel(
            spaceRepository = get()
        )
    }
}