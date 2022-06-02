package common.lifecycle

open class DefaultMutableLifecycleOwner(
    override val lifecycle: MutableLifecycle = MutableLifecycle()
) : MutableLifecycleOwner