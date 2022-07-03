package app.di

import injector.DataInjection
import injector.DataInjectionImpl
import injector.DataInjector
import injector.DataRepository
import injector.impl.BasePnipuInjector
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