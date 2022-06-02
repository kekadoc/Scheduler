package common.lifecycle

interface MutableLifecycleOwner : LifecycleOwner {
    override val lifecycle: MutableLifecycle
}