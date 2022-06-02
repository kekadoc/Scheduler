package common.lifecycle

fun MutableLifecycle.moveToState(state: Lifecycle.State) {
    if (currentState == state) return
    if (currentState.isUpState() && state.isUpState() || currentState.isDownState() && state.isDownState()) {
        if (state.index < currentState.index) {
            throw IllegalLifecycleStateException("It is not possible to switch to previous states")
        } else {
            //println("currentState=$currentState moveState=$state")

            while (currentState != state) {
                //println("curr=$currentState mov=$state")
                val next = currentState.nextState()
                //println("nextSTate=$next")
                setState(next)
            }
        }
    } else {
        val twinState = currentState.twinState()
        if (twinState.index > state.index) {
            throw IllegalLifecycleStateException("It is not possible to switch to previous states")
        } else {
            setState(twinState)
            while (currentState != state) {
                setState(currentState.nextState())
            }
        }
    }
}

fun MutableLifecycle.asLifecycle(): Lifecycle {
    return this
}

fun MutableLifecycle.onCreate() {
    setState(Lifecycle.State.CREATED)
}
fun MutableLifecycle.onStart() {
    setState(Lifecycle.State.STARTED)
}
fun MutableLifecycle.onResume() {
    setState(Lifecycle.State.RESUMED)
}
fun MutableLifecycle.onPause() {
    setState(Lifecycle.State.PAUSED)
}
fun MutableLifecycle.onStop() {
    setState(Lifecycle.State.STOPPED)
}
fun MutableLifecycle.onDestroy() {
    setState(Lifecycle.State.DESTROYED)
}


fun MutableLifecycleOwner.onCreate() {
    lifecycle.onCreate()
}
fun MutableLifecycleOwner.onStart() {
    lifecycle.onStart()
}
fun MutableLifecycleOwner.onResume() {
    lifecycle.onResume()
}
fun MutableLifecycleOwner.onPause() {
    lifecycle.onPause()
}
fun MutableLifecycleOwner.onStop() {
    lifecycle.onStop()
}
fun MutableLifecycleOwner.onDestroy() {
    lifecycle.onDestroy()
}