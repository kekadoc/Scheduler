package common.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import common.view_model.ViewModel
import kotlinx.coroutines.CoroutineScope
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.internal.LazyCreateContainerDecorator
import org.orbitmvi.orbit.internal.RealContainer
import org.orbitmvi.orbit.internal.TestContainerDecorator

// TODO: 02.06.2022

/*@Composable
fun <STATE : Any, SIDE_EFFECT : Any> ContainerHost<STATE, SIDE_EFFECT>.collectSideEffect(
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    sideEffect: (suspend (sideEffect: SIDE_EFFECT) -> Unit)
) {
    val sideEffectFlow = container.sideEffectFlow
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(sideEffectFlow, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(lifecycleState) {
            sideEffectFlow.collect { sideEffect(it) }
        }
    }
}*/

/*@SuppressLint("ComposableNaming")
@Composable
public fun <STATE : Any, SIDE_EFFECT : Any> ContainerHost<STATE, SIDE_EFFECT>.collectState(
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    state: (suspend (state: STATE) -> Unit)
) {
    val stateFlow = container.stateFlow
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(stateFlow, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(lifecycleState) {
            stateFlow.collect { state(it) }
        }
    }
}*/

/*
@Composable
fun <STATE : Any, SIDE_EFFECT : Any> ContainerHost<STATE, SIDE_EFFECT>.collectAsState(): State<STATE> {
    val stateFlow = container.stateFlow
    val lifecycleOwner = LocalLifecycleOwner.current

    val stateFlowLifecycleAware = remember(stateFlow, lifecycleOwner) {
        stateFlow.flowWithLifecycle(lifecycleOwner.lifecycle, lifecycleState)
    }

    // Need to access the initial value to convert to State - collectAsState() suppresses this lint warning too
    @SuppressLint("StateFlowValueCalledInComposition")
    val initialValue = stateFlow.value
    return stateFlowLifecycleAware.collectAsState(initialValue)
}
*/


@Composable
fun <STATE : Any, SIDE_EFFECT : Any> ContainerHost<STATE, SIDE_EFFECT>.collectSideEffect(
    sideEffect: (suspend (sideEffect: SIDE_EFFECT) -> Unit)
) {
    val sideEffectFlow = container.sideEffectFlow

    LaunchedEffect(sideEffectFlow) {
        sideEffectFlow.collect { sideEffect(it) }
    }
}

@Composable
fun <STATE : Any, SIDE_EFFECT : Any> ContainerHost<STATE, SIDE_EFFECT>.collectState() = container.stateFlow.collectAsState()


fun <STATE : Any, SIDE_EFFECT : Any> ViewModel.container(
    initialState: STATE,
    settings: Container.Settings = Container.Settings(),
    onCreate: ((state: STATE) -> Unit)? = null
): Container<STATE, SIDE_EFFECT> = viewModelScope.container(initialState, settings, onCreate)

fun <STATE : Any, SIDE_EFFECT : Any> CoroutineScope.container(
    initialState: STATE,
    settings: Container.Settings = Container.Settings(),
    onCreate: ((state: STATE) -> Unit)? = null
): Container<STATE, SIDE_EFFECT> =
    if (onCreate == null) {
        TestContainerDecorator(
            parentScope = this,
            originalInitialState = initialState,
            actual = RealContainer(
                initialState = initialState,
                settings = settings,
                parentScope = this
            )
        )
    } else {
        TestContainerDecorator(
            parentScope = this,
            originalInitialState = initialState,
            actual = LazyCreateContainerDecorator(
                RealContainer(
                    initialState = initialState,
                    settings = settings,
                    parentScope = this
                ),
                onCreate
            )
        )
    }
