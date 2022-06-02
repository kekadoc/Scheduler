package common.lifecycle

open class DefaultLifecycleOwner(
    override val lifecycle: Lifecycle = Lifecycle()
) : LifecycleOwner