package common.view_model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import common.extensions.cast
import org.koin.core.component.KoinComponent
import kotlin.reflect.KClass

val LocalViewModelStore = staticCompositionLocalOf { ViewModelStore() }

@Composable
inline fun <reified T : ViewModel> viewModel(): T {
    return LocalViewModelStore.current.getViewModel(T::class)
}

class ViewModelStore : KoinComponent {

    private val viewModels = mutableMapOf<KClass<*>, ViewModel>()

    fun <T : ViewModel> getViewModel(clazz: KClass<T>): T {
        val vm = viewModels.getOrPut(clazz) { getKoin().get(clazz) }
        return vm.cast()
    }

    fun clear(clazz: KClass<*>) {
        viewModels[clazz]?.apply {
            clear()
            viewModels.remove(clazz)
        }
    }

    fun clear() {
        viewModels.values.forEach(ViewModel::clear)
        viewModels.clear()
    }

}