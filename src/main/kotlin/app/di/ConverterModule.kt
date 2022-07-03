package app.di

import app.data.converter.DataConverter
import app.data.converter.DataConverterImpl
import org.koin.dsl.module

val dataConverterModule = module {
    single<DataConverter> { DataConverterImpl() }
}