package common.extensions

import common.view_model.ViewModel
import org.koin.core.definition.Definition
import org.koin.core.module.Module

inline fun <reified T : ViewModel> Module.viewModel(noinline definition: Definition<T>) = single<T>(definition = definition)