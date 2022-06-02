package common.lifecycle

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

open class Lifecycle {

    private var _mainScope: CoroutineScope? = null
    val mainScope: CoroutineScope
        get() {
            if (currentState == State.DESTROYED) throw IllegalLifecycleStateException("Lifecycle is destroyed!")
            if (currentState.ordinal < State.CREATED.ordinal) throw IllegalLifecycleStateException("Lifecycle is not created")
            return _mainScope ?: defaultScope().apply { _mainScope = this }
        }

    private var _workScope: CoroutineScope? = null
    val workScope: CoroutineScope
        get() {
            if (currentState == State.DESTROYED) throw IllegalLifecycleStateException("Lifecycle is destroyed!")
            if (currentState == State.STOPPED) throw IllegalLifecycleStateException("Lifecycle is stopped!")
            if (currentState.ordinal < State.STARTED.ordinal) throw IllegalLifecycleStateException("Lifecycle is not started")
            return _workScope ?: defaultScope().apply { _workScope = this }
        }

    private var _presentationScope: CoroutineScope? = null
    val presentationScope: CoroutineScope
        get() {
            if (currentState == State.DESTROYED) throw IllegalLifecycleStateException("Lifecycle is destroyed!")
            if (currentState == State.STOPPED) throw IllegalLifecycleStateException("Lifecycle is stopped!")
            if (currentState == State.PAUSED) throw IllegalLifecycleStateException("Lifecycle is paused!")
            if (currentState.ordinal < State.RESUMED.ordinal) throw IllegalLifecycleStateException("Lifecycle is not resumed")
            return _presentationScope ?: defaultScope().apply { _presentationScope = this }
        }

    private val _state = MutableSharedFlow<State>(
        replay = State.values().size,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    var currentState: State = State.INITIAL
        @Synchronized private set
    val state: Flow<State> = _state.asSharedFlow()


    protected open fun setState(state: State) {
        if (currentState == state) return
        if (currentState == State.DESTROYED) {
            throw IllegalLifecycleStateException("Lifecycle is already destroyed!")
        }
        if (currentState.nextState() != state && currentState.twinState() != state) {
            throw IllegalLifecycleStateException(
                "From the $currentState state, you can only go to the ${currentState.nextState()} or ${currentState.twinState()} states. But $state found"
            )
        }

        this.currentState = state
        this._state.tryEmit(state)
        if (state == State.DESTROYED) {
            _mainScope?.cancel()
            _mainScope = null
            _workScope?.cancel()
            _workScope = null
            _presentationScope?.cancel()
            _presentationScope = null
        }
        if (state == State.STOPPED) {
            _workScope?.cancel()
            _workScope = null
            _presentationScope?.cancel()
            _presentationScope = null
        }
        if (state == State.PAUSED) {
            _presentationScope?.cancel()
            _presentationScope = null
        }
    }

    enum class State(val index: Int) {
        INITIAL(index = 0),
        CREATED(index = 1),
        STARTED(index = 2),
        RESUMED(index = 3),
        PAUSED(index = -3),
        STOPPED(index = -2),
        DESTROYED(index = -1);

        fun isUpState(): Boolean {
            return index >= 0
        }

        fun isDownState(): Boolean {
            return index < 0
        }

        fun nextState(): State {
            return values().find { it.index == index + 1 } ?: this
        }

        fun twinState(): State {
            return values().find { it.index == index * -1 } ?: this
        }
    }

    companion object {
        private fun defaultScope(): CoroutineScope {
            return CoroutineScope(Dispatchers.Default + SupervisorJob())
        }
    }

}