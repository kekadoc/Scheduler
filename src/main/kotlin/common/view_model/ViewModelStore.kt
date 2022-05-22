package common.view_model

import org.koin.core.component.KoinComponent
import kotlin.reflect.KClass

object ViewModelStore : KoinComponent {

    private val viewModels = mutableMapOf<KClass<*>, ViewModel>()

    fun <T : ViewModel> getViewModel(clazz: KClass<T>): T {
        return getKoin().get(clazz)
    }

    fun clear() {
        viewModels.values.forEach(ViewModel::onCleared)
    }

}