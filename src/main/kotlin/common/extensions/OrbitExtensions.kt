package common.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import org.orbitmvi.orbit.ContainerHost

@Composable
fun <STATE : Any, SIDE_EFFECT : Any> ContainerHost<STATE, SIDE_EFFECT>.collectSideEffect(
    sideEffect: (suspend (sideEffect: SIDE_EFFECT) -> Unit)
) {
    val sideEffectFlow = container.sideEffectFlow

    LaunchedEffect(sideEffectFlow) {
        sideEffectFlow.collect { sideEffect(it) }
    }
}