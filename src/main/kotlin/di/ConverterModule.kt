package di

import data.converter.DataConverter
import data.converter.DataConverterImpl
import org.koin.dsl.module

val dataConverterModule = module {
    single<DataConverter> { DataConverterImpl() }
}