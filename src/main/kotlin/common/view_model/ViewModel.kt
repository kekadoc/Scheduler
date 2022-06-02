package common.view_model

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

open class ViewModel {

    val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    protected open fun onCleared() {
        viewModelScope.cancel()
    }

    fun clear() {
        onCleared()
    }
}