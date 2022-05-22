package di

import app.ApplicationViewModel
import org.koin.dsl.module
import ui.InitialScreenViewModel

val viewModelsModule = module {
    factory {
        ApplicationViewModel(
            spaceRepository = get()
        )
    }
    factory {
        InitialScreenViewModel()
    }
}