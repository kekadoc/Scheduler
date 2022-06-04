package common.extensions

import common.view_model.ViewModel
import common.view_model.ViewModelStore
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.definition.Definition
import org.koin.core.module.Module
import org.koin.mp.KoinPlatformTools

inline fun <reified T : ViewModel> Module.viewModel(noinline definition: Definition<T>) = single<T>(definition = definition)

inline fun <reified T : ViewModel> KoinComponent.viewModel(
    mode: LazyThreadSafetyMode = KoinPlatformTools.defaultLazyMode(),
): Lazy<T> {
    return lazy(mode) {
        val viewModelStore = get<ViewModelStore>()
        viewModelStore.getViewModel(T::class)
    }
}