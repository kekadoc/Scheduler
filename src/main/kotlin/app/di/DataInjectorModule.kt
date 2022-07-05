package app.di

import app.data.injector.DataInjection
import app.data.injector.DataInjectionImpl
import app.data.injector.DataInjector
import app.data.injector.DataRepository
import app.data.injector.impl.BasePnipuInjector
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val dataInjectorModule = module {

    single<Set<DataInjector>> {
        setOf(BasePnipuInjector())
    }

    single<DataInjection> {
        DataInjectionImpl(
            dataInjectors = get(),
            spacesRepository = get(),
            provideData = { space -> get(parameters = { parametersOf(space) }) }
        )
    }

    factory { params ->
        DataRepository(
            teachers = get(),
            rooms = get(),
            disciplines = get(),
            groups = get(),
            academicPlan = get()
        )
    }
}