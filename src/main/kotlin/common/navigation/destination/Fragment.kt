package common.navigation.destination

import androidx.compose.runtime.Composable
import common.extensions.requireNotNull
import common.lifecycle.Lifecycle
import common.lifecycle.LifecycleOwner
import common.lifecycle.MutableLifecycle
import common.navigation.graph.Graph

open class Fragment : NestedDestination, LifecycleOwner {

    private val _lifecycle = MutableLifecycle()
    override val lifecycle: Lifecycle = _lifecycle

    init {
        _lifecycle.setState(Lifecycle.State.CREATED)
    }

    private var _parent: Graph? = null
    override var parent: Graph
        get() = _parent.requireNotNull()
        set(value) {
            if (_parent != null) throw IllegalStateException("Parent already injected")
            _parent = value
            _lifecycle.setState(Lifecycle.State.STARTED)
        }

    init {
        _lifecycle.setState(Lifecycle.State.CREATED)
    }

    @Composable
    override fun draw() {}


    final override fun attach() {
        _lifecycle.setState(Lifecycle.State.RESUMED)
    }

    final override fun detach() {
        _lifecycle.setState(Lifecycle.State.PAUSED)
    }

    final override fun destroy() {
        _lifecycle.setState(Lifecycle.State.STOPPED)
    }

}