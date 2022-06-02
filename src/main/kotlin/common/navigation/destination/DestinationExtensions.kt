package common.navigation.destination

import androidx.compose.runtime.Composable
import common.lifecycle.Lifecycle
import common.view_model.LocalViewModelStore
import common.view_model.ViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
inline fun <reified T : ViewModel> Fragment.viewModel(): T {
    val viewModel = LocalViewModelStore.current.getViewModel(T::class)
    lifecycle.state.onEach {
        if (it == Lifecycle.State.DESTROYED) {
            viewModel.clear()
        }
    }.launchIn(lifecycle.mainScope)
    return viewModel
}